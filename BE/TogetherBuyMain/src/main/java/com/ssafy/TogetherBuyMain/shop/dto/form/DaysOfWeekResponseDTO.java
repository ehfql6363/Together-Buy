package com.ssafy.TogetherBuyMain.shop.dto.form;

import java.util.List;

public record DaysOfWeekResponseDTO(
        List<String> daysOfWeek
) {
    public static DaysOfWeekResponseDTO of(List<String> daysOfWeek) {
        return new DaysOfWeekResponseDTO(daysOfWeek);
    }
}
