package com.ssafy.TogetherBuyMain.community.dto.freeboard;

public record FreeBoardRegistrationRequestDTO(
        String freeBoardTitle,
        String freeBoardContent,
        Long productId
) {}
