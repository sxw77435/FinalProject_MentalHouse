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
 * html页面与之关联的接口
 * var reqUrl = "http://localhost:8081/websocket/" + cid;
 * socket = new WebSocket(reqUrl.replace("http", "ws"));
 */
@ServerEndpoint(value = "/websocket/{sid}",configurator= WebSocketConfig.class)
public class WebSocketServer {


    @Autowired
    private ApplicationContextProvider contextProvider;




    /**
     * 静态变量，用来记录当前在线连接数，线程安全的类。
     */
    private static AtomicInteger onlineSessionClientCount = new AtomicInteger(0);

    /**
     * 存放所有在线的客户端
     */
    private static Map<String, Session> onlineSessionClientMap = new ConcurrentHashMap<>();

    /**
     * 连接sid和连接会话
     */
    private String sid;
    private Session session;

    /**
     * 连接建立成功调用的方法。由前端<code>new WebSocket</code>触发
     *
     * @param sid     每次页面建立连接时传入到服务端的id，比如用户id等。可以自定义。
     * @param session 与某个客户端的连接会话，需要通过它来给客户端发送消息
     */
    @OnOpen
    public void onOpen(@PathParam("sid") String sid, Session session, EndpointConfig config) {
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        WebSocketService webSocketService = (WebSocketService) httpSession.getAttribute("webSocketService");


        /**
         * session.getId()：当前session会话会自动生成一个id，从0开始累加的。
         */
        log.info("连接建立中 ==> session_id = {}， sid = {}", session.getId(), sid);
        // 加入 Map 中。将页面的 sid 和 session 绑定或者 session.getId() 与 session
        // onlineSessionIdClientMap.put(session.getId(), session);
        onlineSessionClientMap.put(sid, session);

        // 在线数加1
        onlineSessionClientCount.incrementAndGet();
        this.sid = sid;
        this.session = session;
        sendToOne(sid, "성공적으로 연결했습니다.");
        log.info("连接建立成功，当前在线数为：{} ==> 开始监听新连接：session_id = {}， sid = {},。", onlineSessionClientCount, session.getId(), sid);

    }

    /**
     * 连接关闭调用的方法。由前端<code>socket.close()</code>触发
     *
     * @param sid
     * @param session
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid, Session session) {
        //onlineSessionIdClientMap.remove(session.getId());
        // 从 Map中移除
        onlineSessionClientMap.remove(sid);

        //在线数减1
        onlineSessionClientCount.decrementAndGet();
        log.info("连接关闭成功，当前在线数为：{} ==> 关闭该连接信息：session_id = {}， sid = {},。", onlineSessionClientCount, session.getId(), sid);
    }

    /**
     * 收到客户端消息后调用的方法。由前端<code>socket.send</code>触发
     * * 当服务端执行toSession.getAsyncRemote().sendText(xxx)后，前端的socket.onmessage得到监听。
     *
     * @param message
     * @param session
     */



    @OnMessage
    public void onMessage(String message, Session session) {

        ApplicationContext applicationContext = contextProvider.getApplicationContext();
        WebSocketService webSocketService = applicationContext.getBean(WebSocketService.class);

        /**
         * html界面传递来得数据格式，可以自定义.
         * {"senderId":"user-1","toUserId":"user-2","message":"hello websocket"}
         */
        JSONObject jsonObject = new JSONObject(message);
        String senderId = jsonObject.getString("senderId");
        String receiverId = jsonObject.optString("receiverId");
        String messageContent  = jsonObject.getString("message");

        log.info("服务端收到客户端消息 ==> senderId = {}, receiverId = {}, message = {}", senderId, receiverId, message);

        // 根据接收者情况选择插入逻辑
        if (receiverId == null || receiverId.isEmpty()) {

            int chatroomno = jsonObject.getInt("chatroomno");

            // 处理群发逻辑
            webSocketService.insertGroupMessage(chatroomno,senderId, messageContent);
            sendToAll(messageContent);
        } else {
            // 处理单人聊天逻辑
            webSocketService.insertChat(senderId, receiverId, messageContent);
            sendToOne(receiverId, messageContent);
        }
    }


    /**
     * 发生错误调用的方法
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("WebSocket发生错误，错误信息为：" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 群发消息
     *
     * @param message 消息
     */
    private void sendToAll(String message) {
        // 遍历在线map集合
        onlineSessionClientMap.forEach((onlineSid, toSession) -> {
            // 排除掉自己
            if (!sid.equalsIgnoreCase(onlineSid)) {
                log.info("服务端给客户端群发消息 ==> sid = {}, toSid = {}, message = {}", sid, onlineSid, message);
                toSession.getAsyncRemote().sendText(message);
            }
        });
    }

    /**
     * 指定发送消息
     *
     * @param toSid
     * @param message
     */
    private void sendToOne(String toSid, String message) {
        // 通过sid查询map中是否存在
        Session toSession = onlineSessionClientMap.get(toSid);
        if (toSession == null) {
            log.error("服务端给客户端发送消息 ==> toSid = {} 不存在, message = {}", toSid, message);
            return;
        }
        // 异步发送
        log.info("服务端给客户端发送消息 ==> toSid = {}, message = {}", toSid, message);
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
        // 假设username为sid
        String sid = username;

        // 检查是否存在对应的Session
        if (onlineSessionClientMap.containsKey(sid)) {
            // 存在对应的Session，获取Session对象
            Session userSession = onlineSessionClientMap.get(sid);
            // 判断Session是否打开
            if (userSession.isOpen()) {
                // 如果Session是打开的，发送消息给用户
                String message = "WebSocket session created for user: " + username;
                userSession.getAsyncRemote().sendText(message);
            } else {
                // 如果Session已关闭，记录错误日志或进行其他处理
                System.out.println("Session for user " + username + " is closed.");
            }
        } else {
            // 如果没有找到对应的Session，记录错误日志或进行其他处理
            System.out.println("Session not found for user " + username);
        }
    }
}

