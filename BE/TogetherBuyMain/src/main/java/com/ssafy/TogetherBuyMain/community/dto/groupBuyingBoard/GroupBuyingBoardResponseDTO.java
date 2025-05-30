package com.ssafy.TogetherBuyMain.community.dto.groupBuyingBoard;

import com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoard;
import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.shop.entity.product.Product;

import java.time.LocalDateTime;

public record GroupBuyingBoardResponseDTO(
        Long groupBuyingBoardId,
        String groupBuyingBoardTitle,
        String groupBuyingBoardContent,
        Long currentAmount,
        Long totalAmount,
        String unitName,
        Long likes,
        Long views,
        String productMainImage,
        Long writerId,
        String nickname,
        LocalDateTime createdAt
) {
    public static GroupBuyingBoardResponseDTO of(GroupBuyingBoard groupBuyingBoard) {
        Member member = groupBuyingBoard.getCreator();
        Product product = groupBuyingBoard.getProduct();
        return new GroupBuyingBoardResponseDTO(
                groupBuyingBoard.getGroupBuyingBoardId(),
                groupBuyingBoard.getTitle(),
                groupBuyingBoard.getContent(),
                groupBuyingBoard.getForm() != null ? groupBuyingBoard.getForm().getCurrentAmount() : 0,
                product.getTotal(),
                product.getUnitName(),
                groupBuyingBoard.getLikes(),
                groupBuyingBoard.getViews(),
                product.getImages().isEmpty() ? null : product.getImages().getFirst().getUrl(),
                member.getMemberId(),
                member.getNickname(),
                groupBuyingBoard.getCreatedAt()
        );
    }
}
