package com.ssafy.TogetherBuyMain.community.dto.comment;

import com.ssafy.TogetherBuyMain.community.entity.comment.Comment;
import com.ssafy.TogetherBuyMain.member.entity.Member;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentRegisterResponseDTO(
        Long commentId,
        String commentContent,
        LocalDateTime createdAt,
        String writerName,
        String writerProfile
) {
    public static CommentRegisterResponseDTO of(Comment comment, Member member) {
        return CommentRegisterResponseDTO.builder()
                .commentId(comment.getId())
                .commentContent(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .writerName(member.getNickname())
                .writerProfile(member.getProfileImage().getUrl())
                .build();
    }
}
