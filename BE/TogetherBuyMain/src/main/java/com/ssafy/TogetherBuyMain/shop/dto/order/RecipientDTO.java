package com.ssafy.TogetherBuyMain.shop.dto.order;

import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.member.entity.MemberLocation;

public record RecipientDTO(
        Long memberId,
        String nickname,
        String address
) {
    public static RecipientDTO of(Member member) {
        String address = getStringAddress(member.getMemberLocations().getFirst());
        return new RecipientDTO(member.getMemberId(), member.getNickname(), address);
    }

    private static String getStringAddress(MemberLocation location) {
        return String.join(" ",
                location.getSido(),
                location.getSigungu(),
                location.getEupmyeondong(),
                location.getRi(),
                location.getRoadName(),
                location.getRoadNumber());
    }
}
