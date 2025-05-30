package com.ssafy.TogetherBuyChat.chatting.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class ResponseImageChatMessageDTO {
    private Long chatRoomId;                // MongoDB에서는 숫자 ID를 사용하기 때문에 Long 타입
    private Long senderId;
    private String chatImg;
    private LocalDateTime timestamp;
}