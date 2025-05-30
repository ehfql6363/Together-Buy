package com.ssafy.TogetherBuyMain.community.dto.groupBuyingBoard;

import com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoard;

import java.time.LocalDateTime;

public record GroupBuyingBoardUpdateResponseDTO(
        Long groupBuyingBoardId,
        LocalDateTime updatedAt
) {
    public static GroupBuyingBoardUpdateResponseDTO of(GroupBuyingBoard groupBuyingBoard) {
        return new GroupBuyingBoardUpdateResponseDTO(
                groupBuyingBoard.getGroupBuyingBoardId(),
                groupBuyingBoard.getUpdatedAt()
        );
    }
}
