package com.ssafy.TogetherBuyMain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageResponseDto {
    private long memberId;
    private String nickname;
    private String tel;
    private String birth;
    private int gender;
    private String address;
    private String bankName;
    private String bankNumber;
    private SellerInfo seller;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SellerInfo {
        private String businessName;
        private String bossName;
        private String businessTel;
        private String businessEmail;
        private String businessAddress;
        private String businessNumber;
    }
}
