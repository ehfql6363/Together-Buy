package com.ssafy.TogetherBuyMain.shop.dto.product.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailResponseDTO {
    private Long productId;  // 상품 ID
    private String productName;  // 상품 이름
    private String productImage;  // 상품 이미지
    private String productDetailImage;  // 상품 상세 이미지
    private Long productLikes;  // 좋아요 개수
    private Long price;  // 정가
    private Long salePrice;  // 할인가
    private Long pricePerUnit;  // 단위당 정가
    private Long unitAmount;  // 단위 개수
    private Long currentAmount;  // 현재 구매된 수량
    private String unitName;  // 최소 단위 이름
    private Category category;  // 카테고리
    private List<GroupBuyingInfo> post;  // 공구글 정보
    private SellerInfo seller;  // 판매자 정보
    private Long totalAmount;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Category {
        private String main;  // 카테고리 1
        private String sub;   // 카테고리 2
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GroupBuyingInfo {
        private Long postId;  // 공구글 ID
        private String postTitle;  // 공구글 제목
        private Long currentAmount;  // 현재 공구된 상품 개수
        private Long totalAmount;  // 공구 최대 개수
        private String productLocation;  // 집결 장소
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SellerInfo {
        private Long memberId;  // 판매자 ID (Long으로 변경)
        private String businessName;  // 판매자 이름
        private String profileImage;  // 판매자 사진
    }
}
