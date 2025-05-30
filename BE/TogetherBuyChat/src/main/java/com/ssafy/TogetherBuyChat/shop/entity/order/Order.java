package com.ssafy.TogetherBuyChat.shop.entity.order;

import com.ssafy.TogetherBuyChat.chatting.entity.ChatRoom;
import com.ssafy.TogetherBuyChat.member.entity.Member;
import com.ssafy.TogetherBuyChat.member.entity.Seller;
import com.ssafy.TogetherBuyChat.shop.entity.common.Form;
import com.ssafy.TogetherBuyChat.shop.entity.product.Product;
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
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private long orderPrice; // 주믄 금액
    private int orderQuantity; // 주문 수량
    private String orderAddress; // 주문된 지역구
    private String wayBillNumber; // 운송장 번호
    private LocalDateTime orderDate; // 주문 접수 시간

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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "form_id")
    private Form form;

    public void updateWayBillNumber(String wayBillNumber) {
        this.wayBillNumber = wayBillNumber;
        this.deliveryStatus = DeliveryStatus.DELIVERY;
    }
}
