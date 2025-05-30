package com.ssafy.TogetherBuyMain.community.repository.freeBoard.like;

import com.ssafy.TogetherBuyMain.community.entity.freeBoard.FreeBoard;
import com.ssafy.TogetherBuyMain.member.entity.Member;

public interface FreeBoardLikeRepositoryCustom {
    boolean isLiked(Member member, FreeBoard freeBoard);
    void like(Member member, FreeBoard freeBoard);
    void unlike(Member member, FreeBoard freeBoard);
}
