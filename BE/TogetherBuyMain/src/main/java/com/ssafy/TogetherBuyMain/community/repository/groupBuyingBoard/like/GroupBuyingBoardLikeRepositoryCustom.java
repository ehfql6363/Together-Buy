package com.ssafy.TogetherBuyMain.community.repository.groupBuyingBoard.like;

import com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoard;
import com.ssafy.TogetherBuyMain.member.entity.Member;

public interface GroupBuyingBoardLikeRepositoryCustom {
    boolean isLiked(Member member, GroupBuyingBoard freeBoard);
    void like(Member member, GroupBuyingBoard freeBoard);
    void unlike(Member member, GroupBuyingBoard freeBoard);
}
