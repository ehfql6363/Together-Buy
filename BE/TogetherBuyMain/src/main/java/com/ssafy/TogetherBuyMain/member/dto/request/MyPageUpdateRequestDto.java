package com.ssafy.TogetherBuyMain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageUpdateRequestDto {
    private Long memberId;
    private String nickname;
    private String tel;
    private LocalDate birth;
    private int gender;
    private String address;
    private String bankName;
    private String bankNumber;
    private SellerUpdateInfo seller;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SellerUpdateInfo {
        private String businessName;
        private String bossName;
        private String tel;
        private String businessAddress;
        private String businessNumber;
        private String businessEmail;
    }
}
