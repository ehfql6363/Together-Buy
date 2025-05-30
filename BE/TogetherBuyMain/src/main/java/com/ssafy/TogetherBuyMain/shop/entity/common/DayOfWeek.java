package com.ssafy.TogetherBuyMain.shop.entity.common;

import lombok.Getter;
import java.util.EnumSet;

@Getter
public enum DayOfWeek {
    MONDAY("월요일", false),
    TUESDAY("화요일", false),
    WEDNESDAY("수요일", false),
    THURSDAY("목요일", false),
    FRIDAY("금요일", false),
    SATURDAY("토요일", true),
    SUNDAY("일요일", true);

    private final String koreanName;
    private final boolean isWeekend;

    DayOfWeek(String koreanName, boolean isWeekend) {
        this.koreanName = koreanName;
        this.isWeekend = isWeekend;
    }

    // 주말 여부 확인
    public static boolean isWeekend(DayOfWeek day) {
        return day.isWeekend;
    }

    // 주말 EnumSet 반환
    public static EnumSet<DayOfWeek> getWeekends() {
        return EnumSet.of(SATURDAY, SUNDAY);
    }

    // 평일 EnumSet 반환
    public static EnumSet<DayOfWeek> getWeekdays() {
        return EnumSet.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY);
    }

}
