package com.ssafy.TogetherBuyMain.community.dto.groupBuyingBoard;

import com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoard;
import com.ssafy.TogetherBuyMain.shop.entity.product.Product;

public record GroupBuyingBoardProductDTO(
        Long productId,
        String productName,
        String productImage,
        Long price,
        Long salePrice,
        Long pricePerUnit,
        Long currentAmount,
        Long totalAmount,
        String businessName
) {
    public static GroupBuyingBoardProductDTO of(GroupBuyingBoard groupBuyingBoard) {
        Product product = groupBuyingBoard.getProduct();
        return new GroupBuyingBoardProductDTO(
                product.getProductId(),
                product.getProductName(),
                product.getImages().isEmpty() ? null : product.getImages().getFirst().getUrl(),
                product.getPrice(),
                product.getSalePrice(),
                product.getPricePerUnit(),
                groupBuyingBoard.getForm().getCurrentAmount(),
                product.getTotal(),
                product.getSeller().getBusinessName()
        );
    }
}
