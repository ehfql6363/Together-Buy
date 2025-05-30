package com.ssafy.TogetherBuyBilling.billing.dto;

import com.ssafy.TogetherBuyBilling.billing.entity.PaymentPoint;

public record PointResponseDTO(
        Long point,
        Long cost
) {
    public static PointResponseDTO of(PaymentPoint paymentPoint) {
        return new PointResponseDTO(paymentPoint.getPoint(), paymentPoint.getCost());
    }
}
