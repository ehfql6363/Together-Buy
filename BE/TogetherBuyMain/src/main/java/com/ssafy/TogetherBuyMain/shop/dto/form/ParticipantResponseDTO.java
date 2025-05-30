package com.ssafy.TogetherBuyMain.shop.dto.form;

import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.shop.entity.common.Apply;

public record ParticipantResponseDTO(
        Long recipientId,
        String nickname,
        String profileImg,
        Long purchaseAmount
) {
    public static ParticipantResponseDTO of(Apply apply) {
        Member member = apply.getMember();
        return new ParticipantResponseDTO(
                member.getMemberId(),
                member.getNickname(),
                member.getProfileImage().getUrl(),
                apply.getAmount()
        );
    }
}
