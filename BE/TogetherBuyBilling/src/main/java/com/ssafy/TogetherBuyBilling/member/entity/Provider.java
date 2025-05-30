package com.ssafy.TogetherBuyBilling.member.entity;

import java.util.Arrays;

public enum Provider {
    GOOGLE, NAVER, KAKAO;

    public static Provider toEnum(String provider) {
        return Arrays.stream(Provider.values())
                .filter(p -> p.name().equalsIgnoreCase(provider))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid provider: " + provider));
    }
}
