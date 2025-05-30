package com.ssafy.TogetherBuyMain.member.dto.response;

import com.ssafy.TogetherBuyMain.member.entity.Member;

public record MemberRegisterResponseDTO(
        Long memberId,
        String nickname,
        String imageUrl
) {
    public MemberRegisterResponseDTO(Member member, String imageUrl) {
        this(member.getMemberId(), member.getNickname(), imageUrl);
    }
}