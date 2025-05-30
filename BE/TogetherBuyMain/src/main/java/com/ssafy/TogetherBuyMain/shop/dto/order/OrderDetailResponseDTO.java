package com.ssafy.TogetherBuyMain.shop.dto.order;

import com.ssafy.TogetherBuyMain.shop.entity.order.DeliveryStatus;
import com.ssafy.TogetherBuyMain.shop.entity.order.Order;

import java.time.LocalDate;
import java.util.List;

public record OrderDetailResponseDTO(
        Long orderId,
        Long orderPrice,
        Long orderQuantity,
        DeliveryStatus deliveryStatus,
        String orderAddress,
        LocalDate orderTime,
        RecipientDTO recipient,
        List<OrderParticipantResponseDTO> participants
) {
    public static OrderDetailResponseDTO of(Order order, RecipientDTO recipient, List<OrderParticipantResponseDTO> participants) {
        return new OrderDetailResponseDTO(
                order.getOrderId(),
                order.getOrderPrice(),
                order.getOrderQuantity(),
                order.getDeliveryStatus(),
                order.getOrderAddress(),
                order.getOrderDate(),
                recipient,
                participants
                );
    }
}
