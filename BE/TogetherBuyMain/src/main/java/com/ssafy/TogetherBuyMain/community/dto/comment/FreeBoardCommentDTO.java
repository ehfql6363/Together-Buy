package com.ssafy.TogetherBuyMain.community.dto.comment;

import com.ssafy.TogetherBuyMain.community.entity.comment.Comment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FreeBoardCommentDTO(
        Long commentId,
        String commentContent,
        CommentWriterDTO writer,
        LocalDateTime createdAt
) {
    public static FreeBoardCommentDTO of(Comment comment) {
        return FreeBoardCommentDTO.builder()
                .commentId(comment.getId())
                .commentContent(comment.getContent())
                .writer(CommentWriterDTO.of(comment))
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
