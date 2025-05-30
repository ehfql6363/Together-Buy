package com.ssafy.TogetherBuyNotification.chatting.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Entity
@Document(collection = "chat_messages")
@NoArgsConstructor
@AllArgsConstructor
public class MongoChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chatRoomId;
    private Long senderId;
    private String content;
    private LocalDateTime timestamp;
}