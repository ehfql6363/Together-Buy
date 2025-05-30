package com.ssafy.TogetherBuyMain.shop.dto.product;

public record RegisterProductRequestDTO(
        String productName,
        Long categoryId,
        Long subCategoryId,
        Long price,
        Long discountAmount,
        String disCountUnit,
        Long salePrice,
        Long total,
        Long unitAmount,
        String unitName,
        Long pricePerUnit
) {
}
