package com.ssafy.TogetherBuyChat.chatting.dto;

import com.ssafy.TogetherBuyChat.chatting.dto.DB.ChatMessageDTO;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomDataDTO {
    private List<ChatMessageDTO> chatMessages;
    private List<MemberInfoDTO> memberInfos; // 사용자 정보 리스트
}