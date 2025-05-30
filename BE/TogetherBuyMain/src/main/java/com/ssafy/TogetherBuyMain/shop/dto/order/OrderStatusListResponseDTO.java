package com.ssafy.TogetherBuyMain.shop.dto.order;

import java.util.List;

public record OrderStatusListResponseDTO(
        List<OrderStatusDTO> orders
) {}
