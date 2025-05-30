package com.ssafy.TogetherBuyChat.chatting.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "chat_messages")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MongoChatMessage {

    @Id
    private String id;
    private Long chatRoomId;
    private Long senderId;
    private String content;
    private String chatImg;
    private LocalDateTime timestamp;

    // 생성 시점에 createdAt을 자동으로 설정 JPA에만 해당 MongoDB는 불가능
    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
}