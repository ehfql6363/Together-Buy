package com.ssafy.TogetherBuyMain.community.dto.freeboard;

import com.ssafy.TogetherBuyMain.community.entity.freeBoard.FreeBoard;

import java.time.LocalDateTime;

public record FreeBoardResponseDTO(
        Long freeBoardId,         // 자유 게시글 ID
        String freeBoardTitle,      // 자유 게시글 제목
        String freeBoardContent,    // 자유 게시글 내용
        int commentCount,       // 댓글 수
        Long likes,               // 추천 수
        String freeBoardImages,     // 이미지 주소
        Long writerId,        // 유저 아이디
        String nickname,        // 유저 닉네임
        LocalDateTime updatedAt, // 게시글 생성일
        Long views           // 조회수
) {
    public static FreeBoardResponseDTO of(FreeBoard freeBoard) {
        return new FreeBoardResponseDTO(
                freeBoard.getId(),
                freeBoard.getTitle(),
                freeBoard.getContent(),
                freeBoard.getComments().size(),
                freeBoard.getLikes(),
                freeBoard.getImages().getFirst().getUrl(),
                freeBoard.getMember().getMemberId(),
                freeBoard.getMember().getNickname(),
                freeBoard.getUpdatedAt(),
                freeBoard.getViews());
    }
}
