package com.ssafy.TogetherBuyNotification.notification.service;

import org.springframework.stereotype.Service;

@Service
public class MessageService {

    public void processMessage(String message) {
        System.out.println("Processing message: " + message);

        // TODO: 데이터베이스 저장, 알림 처리, 트랜잭션 관리 등 추가 로직 구현
    }
}
