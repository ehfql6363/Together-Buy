package com.ssafy.TogetherBuyMain.community.dto.freeboard;

import com.ssafy.TogetherBuyMain.member.entity.Member;
import lombok.Builder;

@Builder
public record FreeBoardMemberDTO(
        Long memberId,
        String nickname,
        String profileImage
) {
    public static FreeBoardMemberDTO of(Member member) {
        return FreeBoardMemberDTO.builder()
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage().getUrl())
                .build();
    }
}
