package com.ssafy.TogetherBuyMain.community.repository.comment;

import com.ssafy.TogetherBuyMain.community.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

}
