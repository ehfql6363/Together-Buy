package com.ssafy.TogetherBuyGateway.member.dto.response;


import com.ssafy.TogetherBuyGateway.member.entity.Member;

public record MemberRegisterResponseDTO(
        Long memberId,
        String nickname,
        String imageUrl
) {
    public MemberRegisterResponseDTO(Member member, String imageUrl) {
        this(member.getMemberId(), member.getNickname(), imageUrl);
    }
}