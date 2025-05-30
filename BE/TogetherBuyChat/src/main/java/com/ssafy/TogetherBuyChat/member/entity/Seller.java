package com.ssafy.TogetherBuyChat.member.entity;

import com.ssafy.TogetherBuyChat.shop.entity.order.Order;
import com.ssafy.TogetherBuyChat.shop.entity.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sellerId;

    private String businessName;
    private String bossName;
    private String businessTel;
    private String businessEmail;
    private String businessAddress;
    private String businessNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    // 받은 주문서 (1:N)
    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public void updateSellerInfo(
            String businessName,
            String bossName,
            String businessTel,
            String businessEmail,
            String businessAddress,
            String businessNumber
    ) {
        this.businessName = businessName;
        this.bossName = bossName;
        this.businessTel = businessTel;
        this.businessEmail = businessEmail;
        this.businessAddress = businessAddress;
        this.businessNumber = businessNumber;
    }
}