package com.ssafy.TogetherBuyMain.shop.entity.product;

import com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoard;
import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.member.entity.Seller;
import com.ssafy.TogetherBuyMain.shop.entity.common.ProductImage;
import com.ssafy.TogetherBuyMain.shop.entity.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName; // 상품 이름
    private Long price; // 정가
    private Long discountAmount; // 할인 금액 or 할인 비율
    private String discountUnit; // 할인에 대한 단위 (원 or %)
    private Long salePrice; // 할인 가격
    private Long unitAmount; // 단위량
    private String unitName; // 단위 이름
    private Long pricePerUnit; // 단위 당 가격
    private Long total; // 상품의 총 개수

    // 하위 카테고리 (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id", nullable = false)
    private SubCategory subCategory;

    // 공동 구매 게시글 (1:N)
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupBuyingBoard> groupBuyingBoards = new ArrayList<>();

    // 주문서 (1:N)
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    // 찜한 회원들 (N:M)
    @ManyToMany(mappedBy = "wishedProducts")
    private Set<Member> wishingMembers = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    private String detailImage;

    public void updateProductInfo(
            String productName,
            Long price,
            Long discountAmount,
            String discountUnit,
            Long salePrice,
            Long unitAmount,
            String unitName,
            Long pricePerUnit,
            Long total
    ) {
        this.productName = productName;
        this.price = price;
        this.discountAmount = discountAmount;
        this.discountUnit = discountUnit;
        this.salePrice = salePrice;
        this.unitAmount = unitAmount;
        this.unitName = unitName;
        this.pricePerUnit = pricePerUnit;
        this.total = total;
    }

    public void updateDetailImage(String detailImage) {
        this.detailImage = detailImage;
    }

}