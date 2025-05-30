package com.ssafy.TogetherBuyMain.shop.dto.order;

import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.shop.entity.common.Apply;

public record OrderParticipantResponseDTO(
        Long memberId,
        String nickname,
        Long amount,
        Long cost
) {
    public static OrderParticipantResponseDTO of(Apply apply) {
        Member member = apply.getMember();
        return new OrderParticipantResponseDTO(
                member.getMemberId(),
                member.getNickname(),
                apply.getAmount(),
                apply.getCost());
    }
}
