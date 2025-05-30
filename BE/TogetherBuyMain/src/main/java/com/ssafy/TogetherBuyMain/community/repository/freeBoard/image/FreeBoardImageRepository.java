package com.ssafy.TogetherBuyMain.community.repository.freeBoard.image;

import com.ssafy.TogetherBuyMain.community.entity.freeBoard.FreeBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FreeBoardImageRepository extends JpaRepository<FreeBoardImage, Long>, QuerydslPredicateExecutor<FreeBoardImage> {
}
