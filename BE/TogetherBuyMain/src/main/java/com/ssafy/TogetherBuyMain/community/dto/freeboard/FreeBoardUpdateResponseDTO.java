package com.ssafy.TogetherBuyMain.community.dto.freeboard;

import com.ssafy.TogetherBuyMain.community.entity.freeBoard.FreeBoard;

import java.time.LocalDateTime;

public record FreeBoardUpdateResponseDTO(
        Long postId,
        LocalDateTime updatedAt
) {
    public static FreeBoardUpdateResponseDTO of(FreeBoard freeBoard) {
        return new FreeBoardUpdateResponseDTO(freeBoard.getId(), freeBoard.getUpdatedAt());
    }
}
