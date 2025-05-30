package com.ssafy.TogetherBuyMain.community.repository.comment;

import com.ssafy.TogetherBuyMain.community.entity.comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentRepositoryCustom {
    /**
     * 특정 boardId에 대한 댓글을 페이지네이션하여 조회합니다.
     *
     * @param boardId  조회할 게시글의 ID
     * @param pageable 페이지 요청 정보
     * @return 페이지된 댓글 리스트
     */
    Page<Comment> findCommentsByBoardId(Long boardId, Pageable pageable);
}
