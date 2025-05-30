package com.ssafy.TogetherBuyMain.community.dto.groupBuyingBoard;

import com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoard;
import com.ssafy.TogetherBuyMain.member.entity.Member;

public record GroupBuyingBoardWriterDTO(
        Long writerId,
        String nickname,
        String profileImage
) {
    public static GroupBuyingBoardWriterDTO of(GroupBuyingBoard groupBuyingBoard) {
        Member member = groupBuyingBoard.getCreator();
        return new GroupBuyingBoardWriterDTO(
                member.getMemberId(),
                member.getNickname(),
                member.getProfileImage().getUrl()
        );
    }
}
