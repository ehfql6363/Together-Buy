package com.ssafy.TogetherBuyMain.shop.dto.product;

import com.ssafy.TogetherBuyMain.shop.entity.product.Product;

public record WishedProductDTO(
        Long productId,
        String productName,
        String productMainImage,
        Long unitPrice,
        Long salePrice,
        String unitName,
        String businessName
) {
    public static WishedProductDTO of(Product product) {
        return new WishedProductDTO(
                product.getProductId(),
                product.getProductName(),
                product.getImages().getFirst().getUrl(),
                product.getPricePerUnit(),
                product.getSalePrice(),
                product.getUnitName(),
                product.getSeller().getBusinessName()
        );
    }
}
