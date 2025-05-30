package com.ssafy.TogetherBuyMain.community.repository.freeBoard;

import com.ssafy.TogetherBuyMain.community.entity.freeBoard.FreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long>, QuerydslPredicateExecutor<FreeBoard>, FreeBoardRepositoryCustom {
}
