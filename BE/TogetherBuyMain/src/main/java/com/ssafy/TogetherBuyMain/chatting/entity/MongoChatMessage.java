package com.ssafy.TogetherBuyMain.chatting.entity;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@Entity
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