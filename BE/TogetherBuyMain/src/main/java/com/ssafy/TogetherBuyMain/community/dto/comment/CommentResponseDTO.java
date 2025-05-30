package com.ssafy.TogetherBuyMain.community.dto.comment;

import com.ssafy.TogetherBuyMain.community.entity.comment.Comment;
import com.ssafy.TogetherBuyMain.member.entity.Member;

import java.time.LocalDateTime;

public record CommentResponseDTO(
        Long commentId,
        String commentContent,
        String writerName,
        String writerProfile,
        LocalDateTime createdAt
) {
    public static CommentResponseDTO of(Comment comment) {
        Member member = comment.getMember();
        return new CommentResponseDTO(comment.getId(), comment.getContent(), member.getNickname(), member.getProfileImage().getUrl(), comment.getCreatedAt());
    }
}
