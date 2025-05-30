package com.ssafy.TogetherBuyMain.community.dto.groupBuyingBoard;

import java.util.List;

public record ExpiringGroupBuyingBoardResponseDTO(
        List<ExpiringGroupBuyingBoardDTO> expiringGroupBuyingBoards
) {
    public ExpiringGroupBuyingBoardResponseDTO of(List<ExpiringGroupBuyingBoardDTO> almostGroupBuyingBoards) {
        return new ExpiringGroupBuyingBoardResponseDTO(almostGroupBuyingBoards);
    }
}
