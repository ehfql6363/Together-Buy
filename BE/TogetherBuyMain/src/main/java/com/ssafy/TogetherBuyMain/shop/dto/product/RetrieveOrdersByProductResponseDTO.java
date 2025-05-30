package com.ssafy.TogetherBuyMain.shop.dto.product;

import com.ssafy.TogetherBuyMain.shop.dto.order.OrderDetailResponseDTO;
import com.ssafy.TogetherBuyMain.shop.dto.order.OrderListDTO;

import java.util.List;

public record RetrieveOrdersByProductResponseDTO(
        List<OrderListDTO> orders,
        OrderDetailResponseDTO order,
        Long total
) {
    public static RetrieveOrdersByProductResponseDTO of(List<OrderListDTO> orders, OrderDetailResponseDTO order, Long total) {
        return new RetrieveOrdersByProductResponseDTO(orders, order, total);
    }
}
