package com.ssafy.TogetherBuyMain.community.dto.freeboard;

import com.ssafy.TogetherBuyMain.shop.entity.product.Product;
import lombok.Builder;

@Builder
public record FreeBoardProductDTO(
        Long productId,
        String productName,
        String productImage,
        Long price,
        Long salePrice
) {
    public static FreeBoardProductDTO of(Product product) {
        return FreeBoardProductDTO.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productImage(product.getImages().getFirst().getUrl())
                .price((long) product.getPrice())
                .salePrice((long) product.getSalePrice())
                .build();
    }
}
