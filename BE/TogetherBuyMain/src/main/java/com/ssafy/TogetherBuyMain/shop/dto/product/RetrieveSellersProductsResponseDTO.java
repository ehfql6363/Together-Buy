package com.ssafy.TogetherBuyMain.shop.dto.product;

import com.ssafy.TogetherBuyMain.shop.entity.product.Product;
import com.ssafy.TogetherBuyMain.shop.entity.product.SubCategory;

public record RetrieveSellersProductsResponseDTO(
        Long productId,
        String productName,
        String productMainImage,
        String category,
        String subCategory,
        Long price,
        Long salePrice
) {
    public static RetrieveSellersProductsResponseDTO of(Product product) {
        SubCategory subCategory = product.getSubCategory();
        return new RetrieveSellersProductsResponseDTO(
                product.getProductId(),
                product.getProductName(),
                product.getImages().getFirst().getUrl(),
                subCategory.getCategory().getCategoryName(),
                subCategory.getName(),
                product.getPrice(),
                product.getSalePrice()
        );
    }
}
