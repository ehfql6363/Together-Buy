package com.ssafy.TogetherBuyMain.member.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MemberRegisterRequestDTO(
        String nickname,
//        LocalDateTime birth,
        LocalDate birth,
        String gender,
        String address,
        String bankName,
        String bankNumber,
        String tel
) {
    public String toString() {
        return "MemberRegisterRequestDTO{" +
                "nickname='" + nickname + '\'' +
                ", birth=" + birth +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankNumber='" + bankNumber + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
