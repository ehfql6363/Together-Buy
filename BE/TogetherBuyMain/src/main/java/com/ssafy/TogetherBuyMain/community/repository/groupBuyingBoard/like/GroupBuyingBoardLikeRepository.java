package com.ssafy.TogetherBuyMain.community.repository.groupBuyingBoard.like;

import com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupBuyingBoardLikeRepository extends JpaRepository<GroupBuyingBoardLike, Long>, GroupBuyingBoardLikeRepositoryCustom {
}
