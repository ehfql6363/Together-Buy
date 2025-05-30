package com.ssafy.TogetherBuyMain.community.repository.freeBoard.like;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.TogetherBuyMain.community.entity.freeBoard.FreeBoard;
import com.ssafy.TogetherBuyMain.community.entity.freeBoard.FreeBoardLike;
import com.ssafy.TogetherBuyMain.community.entity.freeBoard.QFreeBoardLike;
import com.ssafy.TogetherBuyMain.community.repository.freeBoard.FreeBoardRepository;
import com.ssafy.TogetherBuyMain.member.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public class FreeBoardLikeRepositoryImpl implements FreeBoardLikeRepositoryCustom {

    private final FreeBoardRepository freeBoardRepository;

    @PersistenceContext
    private EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    public FreeBoardLikeRepositoryImpl(EntityManager entityManager, FreeBoardRepository freeBoardRepository) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
        this.freeBoardRepository = freeBoardRepository;
    }

    @Override
    @Transactional
    public boolean isLiked(Member member, FreeBoard freeBoard) {
        QFreeBoardLike qFreeBoardLike = QFreeBoardLike.freeBoardLike;
        BooleanExpression isMember = qFreeBoardLike.member.eq(member);
        BooleanExpression isFreeBoard = qFreeBoardLike.freeBoard.eq(freeBoard);

        return queryFactory
                .select(qFreeBoardLike.member)
                .from(qFreeBoardLike)
                .where(isMember.and(isFreeBoard))
                .fetchFirst() != null;
    }

    @Override
    @Transactional
    public void like(Member member, FreeBoard freeBoard) {
        FreeBoardLike freeBoardLike = FreeBoardLike.builder()
                .member(member)
                .freeBoard(freeBoard)
                .createdAt(LocalDateTime.now()).build();

        entityManager.persist(freeBoardLike);
        entityManager.flush();

        freeBoard.updateLikes();

        freeBoardRepository.save(freeBoard);

    }

    @Override
    @Transactional
    public void unlike(Member member, FreeBoard freeBoard) {
        QFreeBoardLike qFreeBoardLike = QFreeBoardLike.freeBoardLike;
        queryFactory
                .delete(qFreeBoardLike)
                .where(qFreeBoardLike.member.eq(member)
                        .and(qFreeBoardLike.freeBoard.eq(freeBoard)))
                .execute();

        freeBoard.updateLikes();

        freeBoardRepository.save(freeBoard);
    }
}
