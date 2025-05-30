package com.ssafy.TogetherBuyMain.community.repository.freeBoard;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.TogetherBuyMain.community.dto.freeboard.HotFreeBoardDTO;
import com.ssafy.TogetherBuyMain.community.entity.freeBoard.QFreeBoard;
import com.ssafy.TogetherBuyMain.community.entity.freeBoard.QFreeBoardImage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class FreeBoardRepositoryImpl implements FreeBoardRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private final JPAQueryFactory queryFactory;

    public FreeBoardRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

//  예시: 게시글 제목, 내용, 수정 일자를 변경하는 메서드
    @Override
    @Transactional
    public long updateFreeBoard(Long id, String newTitle, String newContent, LocalDateTime updatedAt) {
        QFreeBoard qFreeBoard = QFreeBoard.freeBoard;

        return queryFactory
                .update(qFreeBoard)
                .where(qFreeBoard.id.eq(id))
                .set(qFreeBoard.title, newTitle)
                .set(qFreeBoard.content, newContent)
                .set(qFreeBoard.updatedAt, updatedAt)
                .execute();
    }

    @Override
    @Transactional
    public long deleteFreeBoard(Long id) {
        QFreeBoard qFreeBoard = QFreeBoard.freeBoard;

        return queryFactory
                .delete(qFreeBoard)
                .where(qFreeBoard.id.eq(id))
                .execute();
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<HotFreeBoardDTO> findHotFreeBoard() {
        QFreeBoard qFreeBoard = QFreeBoard.freeBoard;
        QFreeBoardImage qFreeBoardImage = QFreeBoardImage.freeBoardImage;

        return queryFactory.select(Projections.constructor(
                        HotFreeBoardDTO.class,
                        qFreeBoard.id,
                        qFreeBoard.title,
                        qFreeBoard.content,
                        qFreeBoardImage.url,
                        qFreeBoard.freeBoardLikes.size(),
                        qFreeBoard.comments.size()
                ))
                .from(qFreeBoard)
                .join(qFreeBoard.images, qFreeBoardImage)
                .where(greaterThen30Likes(qFreeBoard))
                .fetch();
    }

    private BooleanExpression greaterThen30Likes(QFreeBoard qFreeBoard) {
        return qFreeBoard.likes.gt(0);
    }
}

