package com.ssafy.TogetherBuyMain.community.dto.comment;

import com.ssafy.TogetherBuyMain.community.entity.comment.Comment;
import lombok.Builder;

@Builder
public record CommentWriterDTO(
        Long id,
        String nickname
) {
    public static CommentWriterDTO of(Comment comment) {
        return CommentWriterDTO.builder()
                .id(comment.getMember().getMemberId())
                .nickname(comment.getMember().getNickname())
                .build();
    }
}
