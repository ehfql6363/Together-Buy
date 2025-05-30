package com.ssafy.TogetherBuyMain.shop.dto.product;

public record ProductUpdateRequestDTO(
        String productName,
        String category,
        String subCategory,
        Long price,
        Long discountAmount,
        String discountUnit,
        Long salePrice,
        Long unitAmount,
        String unitName,
        Long pricePerUnit,
        Long total
) {
}
