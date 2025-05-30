package com.ssafy.TogetherBuyMain.shop.dto.form;

import java.util.List;

public record UpdateDaysOfWeekRequestDTO(
        List<String> daysOfWeek
) {
}
