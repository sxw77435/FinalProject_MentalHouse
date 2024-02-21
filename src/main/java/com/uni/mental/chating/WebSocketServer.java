package com.uni.mental.chating;

import com.uni.mental.chating.model.service.WebSocketService;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
/**
 * var reqUrl = "http://localhost:8081/websocket/" + cid;
 * socket = new WebSocket(reqUrl.replace("http", "ws"));
 */
@ServerEndpoint(value = "/websocket/{sid}",configurator= WebSocketConfig.class)
public class WebSocketServer {


    @Autowired
    private ApplicationContextProvider contextProvider;




    /**
     * 현재 온라인 연결 수를 기록하는 정적 변수입니다. 스레드 안전한 클래스입니다.
     */
    private static AtomicInteger onlineSessionClientCount = new AtomicInteger(0);

    /**
     * 모든 온라인 클라이언트를 보관하는 곳입니다.
     */
    private static Map<String, Session> onlineSessionClientMap = new ConcurrentHashMap<>();

    /**
     * 연결 SID와 연결 세션을 매핑하는 곳입니다.
     */
    private String sid;
    private Session session;

    /**

     연결이 성공적으로 설정될 때 호출되는 메서드입니다. 프론트엔드의 <code>new WebSocket</code>으로부터 트리거됩니다.
     @param sid 서버에 전달되는 각 페이지마다의 연결 ID입니다. 사용자 ID 등을 포함할 수 있습니다. 사용자 정의가 가능합니다.
     @param session 특정 클라이언트와의 연결 세션입니다. 이를 통해 클라이언트에게 메시지를 전송할 수 있습니다.
     */
    @OnOpen
    public void onOpen(@PathParam("sid") String sid, Session session, EndpointConfig config) {
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        WebSocketService webSocketService = (WebSocketService) httpSession.getAttribute("webSocketService");


        /**
         session.getId(): 현재 세션에서 자동으로 생성된 ID입니다. 0부터 시작하여 증가합니다.
         */
        log.info("연결 설정 중입니다 ==> session_id = {}， sid = {}", session.getId(), sid);
        // 加入 Map 中。将页面的 sid 和 session 绑定或者 session.getId() 与 session
        // onlineSessionIdClientMap.put(session.getId(), session);
        onlineSessionClientMap.put(sid, session);

        // 在线数加1
        onlineSessionClientCount.incrementAndGet();
        this.sid = sid;
        this.session = session;
        sendToOne(sid, "성공적으로 연결했습니다.");
        log.info("연결이 성공적으로 설정되었습니다. 현재 온라인 수: {} ==> 새로운 연결 수신 대기 중: session_id = {}， sid = {}。", onlineSessionClientCount, session.getId(), sid);

    }


    @OnClose
    public void onClose(@PathParam("sid") String sid, Session session) {
        //onlineSessionIdClientMap.remove(session.getId());
        // 从 Map中移除
        onlineSessionClientMap.remove(sid);

        //在线数减1
        onlineSessionClientCount.decrementAndGet();
        log.info("연결이 성공적으로 닫혔습니다. 현재 온라인 수: {} ==> 연결 닫힘 정보: session_id = {}， sid = {}。", onlineSessionClientCount, session.getId(), sid);
    }

    /**

     클라이언트 메시지를 수신하면 호출되는 메서드입니다. 프론트엔드의 <code>socket.send</code>로부터 트리거됩니다.
     서버가 toSession.getAsyncRemote().sendText(xxx)를 실행하면 프론트엔드의 socket.onmessage가 대기합니다.
     @param message 수신된 메시지입니다.
     @param session 클라이언트와의 연결 세션입니다.
     */

    @OnMessage
    public void onMessage(String message, Session session) {

        ApplicationContext applicationContext = contextProvider.getApplicationContext();
        WebSocketService webSocketService = applicationContext.getBean(WebSocketService.class);

        /**
         * {"senderId":"user-1","toUserId":"user-2","message":"hello websocket"}
         */
        JSONObject jsonObject = new JSONObject(message);
        String senderId = jsonObject.getString("senderId");
        String receiverId = jsonObject.optString("receiverId");
        String messageContent  = jsonObject.getString("message");

        log.info("서버가 클라이언트 메시지를 수신했습니다 ==> 발신자 ID = {}, 수신자 ID = {}, 메시지 = {}", senderId, receiverId, message);


        if (receiverId == null || receiverId.isEmpty()) {

            int chatroomno = jsonObject.getInt("chatroomno");


            webSocketService.insertGroupMessage(chatroomno,senderId, messageContent);
            sendToAll(messageContent);
        } else {

            webSocketService.insertChat(senderId, receiverId, messageContent);
            sendToOne(receiverId, messageContent);
        }
    }


    @OnError
    public void onError(Session session, Throwable error) {
        log.error("WebSocket에서 오류가 발생했습니다. 오류 메시지는 다음과 같습니다: " + error.getMessage());
        error.printStackTrace();
    }


    private void sendToAll(String message) {
        // 遍历在线map集合
        onlineSessionClientMap.forEach((onlineSid, toSession) -> {
            // 排除掉自己
            if (!sid.equalsIgnoreCase(onlineSid)) {
                log.info("서버가 클라이언트에게 메시지를 그룹으로 전송했습니다 ==> sid = {}, toSid = {}, 메시지 = {}", sid, onlineSid, message);
                toSession.getAsyncRemote().sendText(message);
            }
        });
    }

    private void sendToOne(String toSid, String message) {

        Session toSession = onlineSessionClientMap.get(toSid);
        if (toSession == null) {
            log.error("서버가 클라이언트에게 메시지를 전송하려고 시도했지만 toSid = {}가 존재하지 않습니다. 메시지 = {}", toSid, message);
            return;
        }

        log.info("서버가 클라이언트에게 메시지를 그룹으로 전송했습니다 ==> sid = {}, toSid = {}, 메시지 = {}", toSid, message);
        toSession.getAsyncRemote().sendText(message);
        /*
        // 同步发送
        try {
            toSession.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("发送消息失败，WebSocket IO异常");
            e.printStackTrace();
        }*/
    }

    public void createWebSocketSession(String username) {

        String sid = username;


        if (onlineSessionClientMap.containsKey(sid)) {

            Session userSession = onlineSessionClientMap.get(sid);

            if (userSession.isOpen()) {

                String message = "WebSocket session created for user: " + username;
                userSession.getAsyncRemote().sendText(message);
            } else {

                System.out.println("Session for user " + username + " is closed.");
            }
        } else {
    
            System.out.println("Session not found for user " + username);
        }
    }
}

