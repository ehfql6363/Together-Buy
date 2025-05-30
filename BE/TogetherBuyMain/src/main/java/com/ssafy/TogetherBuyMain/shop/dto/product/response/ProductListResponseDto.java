package com.ssafy.TogetherBuyMain.shop.dto.product.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductListResponseDto {
    private List<ProductInfo> products;  // 상품 목록

    // 내부 정적 클래스로 상품 정보 정의
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductInfo {
        private Long productId;           // 상품 PK
        private String productName;       // 상품명
        private Long price;              // 정가
        private Long unitAmount;         // 단위 개수
        private String unitName;         // 단위
        private Long pricePerUnit;       // 단위 당 가격
        private String productMainImage; // 상품 메인 이미지
    }
}