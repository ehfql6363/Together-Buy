package com.ssafy.TogetherBuyChat.chatting.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class JoinChatMessageDTO {
    private Long chatRoomId;                // MongoDB에서는 숫자 ID를 사용하기 때문에 Long 타입
    private Long senderId;
    private String content;
    private String imgUrl;                // 이미지 쓸 거면 URL 필요
    private LocalDateTime timestamp;
}
