package com.ssafy.TogetherBuyMain.community.dto.groupBuyingBoard;

import java.util.List;

public record GroupBuyingBoardRegisterRequestDTO(
        String title,
        String content,
        Long productId
) {
}
