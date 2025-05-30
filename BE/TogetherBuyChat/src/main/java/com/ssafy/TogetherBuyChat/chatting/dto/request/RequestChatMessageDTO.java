package com.ssafy.TogetherBuyChat.chatting.dto.request;

public record RequestChatMessageDTO(
        Long chatRoomId,
        Long senderId,
        String content
) {}

