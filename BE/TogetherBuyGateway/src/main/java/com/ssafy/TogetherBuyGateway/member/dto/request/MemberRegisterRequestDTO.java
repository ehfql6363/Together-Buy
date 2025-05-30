package com.ssafy.TogetherBuyGateway.member.dto.request;

import java.time.LocalDate;

public record MemberRegisterRequestDTO(
        String nickname,
//        LocalDateTime birth,
        LocalDate birth,
        Integer gender,
        String sido,
        String sigungu,
        String eupmyeondong,
        String ri,
        String loadName,
        String loadNumber,
        String bankName,
        String bankNumber,
        String tel
) {
}
