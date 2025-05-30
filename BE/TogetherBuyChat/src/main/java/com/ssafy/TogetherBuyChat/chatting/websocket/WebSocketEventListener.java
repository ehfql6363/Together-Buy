package com.ssafy.TogetherBuyChat.chatting.websocket;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {   // 웹소켓 접속 시
        System.out.println("🔵 User Connected");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {   // 웹소켓 종료 시
        System.out.println("🔴 User Disconnected");
    }

    private String getUserName(StompHeaderAccessor stompHeaderAccessor) {
        if (stompHeaderAccessor.getUser() != null) {
            return stompHeaderAccessor.getUser().getName();   // Principal에서 가져오기
        } else {
            return stompHeaderAccessor.getFirstNativeHeader("username"); // STOMP 헤더에서 가져오기
        }
    }
}
