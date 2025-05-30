package com.ssafy.TogetherBuyBilling.billing.dto;

public record PointChargeResponseDTO(
        Long currentPoint,
        String pointName,
        Long cost
) {
    public static PointChargeResponseDTO of(Long currentPoint, Long point, Long cost) {
        return new PointChargeResponseDTO(currentPoint + point, point + " pt", cost);
    }
}
