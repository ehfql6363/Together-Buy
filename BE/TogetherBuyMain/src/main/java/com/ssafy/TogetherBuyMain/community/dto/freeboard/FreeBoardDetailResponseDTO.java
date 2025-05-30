package com.ssafy.TogetherBuyMain.community.dto.freeboard;

import com.ssafy.TogetherBuyMain.community.dto.comment.FreeBoardCommentDTO;
import com.ssafy.TogetherBuyMain.community.entity.freeBoard.FreeBoard;
import com.ssafy.TogetherBuyMain.shop.entity.product.Product;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record FreeBoardDetailResponseDTO(
        Long freeBoardId,         // 자유 게시글 ID
        String freeBoardTitle,      // 자유 게시글 제목
        String freeBoardContent,    // 자유 게시글 내용
        String freeBoardImages,     // 이미지 주소
        Long freeBoardViews,           // 조회수
        Long freeBoardLikes,               // 추천 수
        LocalDateTime freeBoardUpdatedAt, // 게시글 수정일
        Boolean hasProduct,
        FreeBoardProductDTO product,
        List<FreeBoardCommentDTO> comments,
        FreeBoardMemberDTO member
) {
    public static FreeBoardDetailResponseDTO of(FreeBoard freeBoard) {
        return FreeBoardDetailResponseDTO.builder()
                .freeBoardId(freeBoard.getId())
                .freeBoardTitle(freeBoard.getTitle())
                .freeBoardContent(freeBoard.getContent())
                .freeBoardImages(freeBoard.getImages().getFirst().getUrl())
                .freeBoardViews(freeBoard.getViews())
                .freeBoardLikes(freeBoard.getLikes())
                .freeBoardUpdatedAt(freeBoard.getUpdatedAt())
                .hasProduct(hasProduct(freeBoard.getProduct()))
                .product(!hasProduct(freeBoard.getProduct()) ? null : FreeBoardProductDTO.of(freeBoard.getProduct()))
                .comments(freeBoard.getComments().stream().map(FreeBoardCommentDTO::of).toList())
                .member(FreeBoardMemberDTO.of(freeBoard.getMember()))
                .build();
    }

    private static Boolean hasProduct(Product product){
        return product != null;
    }
}
