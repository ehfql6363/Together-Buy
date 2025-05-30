package com.ssafy.TogetherBuyChat.chatting.dto.request;

import lombok.Builder;

@Builder
public record RequestJoinMemberDTO(
        Long memberId,
        String nickname,
        String url,
        String message
) {
}
