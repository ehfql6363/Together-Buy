package com.ssafy.TogetherBuyChat.chatting.dto.request;

public record RequestMemberIdDTO(
        Long memberId,
        String nickname,
        String url
) { }
