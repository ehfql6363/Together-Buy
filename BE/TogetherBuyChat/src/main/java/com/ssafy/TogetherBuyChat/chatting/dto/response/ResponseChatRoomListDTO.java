package com.ssafy.TogetherBuyChat.chatting.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
public class ResponseChatRoomListDTO {
    private Set<Long> chatRoomIds;
}
