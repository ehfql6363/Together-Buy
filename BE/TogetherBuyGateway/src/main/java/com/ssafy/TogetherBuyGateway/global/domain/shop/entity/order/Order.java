package com.ssafy.TogetherBuyGateway.global.domain.shop.entity.order;

import com.ssafy.TogetherBuyGateway.global.domain.shop.entity.common.Form;
import com.ssafy.TogetherBuyGateway.global.domain.shop.entity.product.Product;
import com.ssafy.TogetherBuyGateway.member.entity.Member;
import com.ssafy.TogetherBuyGateway.member.entity.Seller;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long orderPrice; // 주믄 금액
    private Long orderQuantity; // 주문 수량
    private String orderAddress; // 주문된 지역구
    private String wayBillNumber; // 운송장 번호
    private LocalDate orderDate; // 주문 접수 시간

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus; // 배송 상태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product; // 주문 상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "form_id")
    private Form form;

    public void updateWayBillNumber(String wayBillNumber) {
        this.wayBillNumber = wayBillNumber;
        this.deliveryStatus = DeliveryStatus.DELIVERY;
    }

    public void updateForm(Form form) {
        this.form = form;
    }
}
