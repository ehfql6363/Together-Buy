package com.ssafy.TogetherBuyMain.shop.dto.order;

import com.ssafy.TogetherBuyMain.shop.entity.order.DeliveryStatus;
import com.ssafy.TogetherBuyMain.shop.entity.order.Order;

public record OrderStatusDTO(
        Long orderId,
        String productName,
        String productMainImage,
        String address,
        DeliveryStatus status
) {
    public static OrderStatusDTO of(Order order) {
        return new OrderStatusDTO(
                order.getOrderId(),
                order.getProduct().getProductName(),
                order.getProduct().getImages().getFirst().getUrl(),
                order.getOrderAddress(),
                order.getDeliveryStatus()
        );
    }
}
