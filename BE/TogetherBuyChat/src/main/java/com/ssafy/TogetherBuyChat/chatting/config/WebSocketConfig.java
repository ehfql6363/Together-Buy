package com.ssafy.TogetherBuyChat.chatting.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*");
//                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //  메모리 기반의 메시지 브로커를 사용하며, "/sub" 접두사로 시작하는 목적지를 구독 주제로 설정
        registry.enableSimpleBroker("/sub");
        // 클라이언트에서 서버로 메시지를 발행할 때 사용할 접두사를 "/pub"으로 설정
        registry.setApplicationDestinationPrefixes("/pub");
    }
}
