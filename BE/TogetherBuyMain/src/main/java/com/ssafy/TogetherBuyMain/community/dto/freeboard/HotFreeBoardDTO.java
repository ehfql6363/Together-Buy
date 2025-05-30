package com.ssafy.TogetherBuyMain.community.dto.freeboard;

import com.ssafy.TogetherBuyMain.community.entity.freeBoard.FreeBoard;

public record HotFreeBoardDTO(
        Long freeBoardId,
        String freeBoardTitle,
        String freeBoardContent,
        String freeBoardImage,
        Integer freeBoardLikesCnt,
        Integer commentCount

) {
    public static HotFreeBoardDTO of(FreeBoard freeBoard) {
        return new HotFreeBoardDTO(
                freeBoard.getId(),
                freeBoard.getTitle(),
                freeBoard.getContent(),
                freeBoard.getImages().getFirst().getUrl(),
                freeBoard.getFreeBoardLikes().size(),  // 추천 수
                freeBoard.getComments().size()
        );
    }
}
