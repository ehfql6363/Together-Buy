package com.ssafy.TogetherBuyMain.shop.dto.form;

import com.ssafy.TogetherBuyMain.member.entity.Member;

public record RecipientResponseDTO(
        Long recipientId,
        String nickname,
        String profileImg
) {
    public static RecipientResponseDTO of(Member member) {
        return new RecipientResponseDTO(
                member.getMemberId(),
                member.getNickname(),
                member.getProfileImage().getUrl()
        );
    }
}
