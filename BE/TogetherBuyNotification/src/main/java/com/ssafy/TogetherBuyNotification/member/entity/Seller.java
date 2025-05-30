package com.ssafy.TogetherBuyNotification.member.entity;

import com.ssafy.TogetherBuyNotification.shop.entity.order.Order;
import com.ssafy.TogetherBuyNotification.shop.entity.product.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "sellers")
@NoArgsConstructor
@AllArgsConstructor
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

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    // 받은 주문서 (1:N)
    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @Builder
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