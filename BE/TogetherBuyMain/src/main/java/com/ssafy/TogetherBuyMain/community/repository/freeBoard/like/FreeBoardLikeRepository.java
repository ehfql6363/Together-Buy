package com.ssafy.TogetherBuyMain.community.repository.freeBoard.like;

import com.ssafy.TogetherBuyMain.community.entity.freeBoard.FreeBoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeBoardLikeRepository extends JpaRepository<FreeBoardLike, Long>, FreeBoardLikeRepositoryCustom {
}
