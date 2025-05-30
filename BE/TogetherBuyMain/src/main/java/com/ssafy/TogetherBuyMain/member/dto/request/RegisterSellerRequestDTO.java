package com.ssafy.TogetherBuyMain.member.dto.request;

public record RegisterSellerRequestDTO(
        String businessName,
        String bossName,
        String businessTel,
        String businessEmail,
        String businessAddress,
        String businessNumber
) {
}
