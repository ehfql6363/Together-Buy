package com.ssafy.TogetherBuyMain.community.dto.freeboard;

public record FreeBoardUpdateRequestDTO(
        String freeBoardTitle,
        String freeBoardContent,
        Long productId
) {}
