package com.ssafy.TogetherBuyMain.shop.dto.product;

import com.ssafy.TogetherBuyMain.shop.entity.common.ProductImage;
import com.ssafy.TogetherBuyMain.shop.entity.product.Product;
import com.ssafy.TogetherBuyMain.shop.entity.product.SubCategory;

import java.util.List;

public record ProductModifyResponseDTO(
        Long productId,
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
        List<String> productImages,
        String productDetailImage,
        Long total
) {
    public static ProductModifyResponseDTO of(Product product) {
        SubCategory subCategory = product.getSubCategory();
        return new ProductModifyResponseDTO(
                product.getProductId(),
                product.getProductName(),
                subCategory.getCategory().getCategoryName(),
                subCategory.getName(),
                product.getPrice(),
                product.getDiscountAmount(),
                product.getDiscountUnit(),
                product.getSalePrice(),
                product.getUnitAmount(),
                product.getUnitName(),
                product.getPricePerUnit(),
                getProductImages(product),
                product.getDetailImage(),
                product.getTotal()
        );
    }

    private static List<String> getProductImages(Product product) {
        List<ProductImage> productImages = product.getImages();
        return productImages.stream().map(ProductImage::getUrl).toList();
    }
}
