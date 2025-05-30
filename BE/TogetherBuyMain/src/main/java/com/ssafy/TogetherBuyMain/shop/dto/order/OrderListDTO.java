package com.ssafy.TogetherBuyMain.shop.dto.order;

import com.ssafy.TogetherBuyMain.shop.entity.order.Order;

public record OrderListDTO(
        Long orderId,
        String orderLocation,
        Long orderPrice
) {
    public static OrderListDTO of(Order order) {
        return new OrderListDTO(
                order.getOrderId(),
                order.getOrderAddress(),
                order.getOrderPrice()
        );
    }
}
