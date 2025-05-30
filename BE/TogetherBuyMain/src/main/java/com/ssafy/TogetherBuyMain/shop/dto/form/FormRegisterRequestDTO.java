package com.ssafy.TogetherBuyMain.shop.dto.form;

import java.util.List;

public record FormRegisterRequestDTO(
        Long amount,
        List<String> dayOfWeeks,
        Long startTime,
        Long endTime,
        String address
) {
}
