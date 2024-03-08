package com.uni.mental.chating;


import com.uni.mental.chating.model.service.WebSocketService;
import com.uni.mental.member.model.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig extends ServerEndpointConfig.Configurator {

    @Autowired
    private WebSocketService webSocketService;
    @Autowired
    private MemberService memberService;

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {

        //httpsession 얻기
        // WebSocketService계속 null 이고 그래서 httpsession 통해서 가져오기
        HttpSession session = (HttpSession) request.getHttpSession();
        sec.getUserProperties().put(HttpSession.class.getName(), session);
        session.setAttribute("webSocketService", webSocketService);
        session.setAttribute("memberService", memberService);


    }


    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}

