package com.ssafy.TogetherBuyMain.community.dto.groupBuyingBoard;

import java.util.List;

public record AppliedGroupBuyingBoardResponseDTO(
        List<AppliedGroupBuyingBoardDTO> appliedGroupBuyingBoards
) {
    public AppliedGroupBuyingBoardResponseDTO of(List<AppliedGroupBuyingBoardDTO> appliedGroupBuyingBoards) {
        return new AppliedGroupBuyingBoardResponseDTO(appliedGroupBuyingBoards);
    }
}
