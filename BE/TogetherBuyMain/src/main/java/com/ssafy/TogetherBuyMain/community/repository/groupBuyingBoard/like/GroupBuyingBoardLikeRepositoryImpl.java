package com.ssafy.TogetherBuyMain.community.repository.groupBuyingBoard.like;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoard;
import com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoardLike;
import com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.QGroupBuyingBoardLike;
import com.ssafy.TogetherBuyMain.member.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GroupBuyingBoardLikeRepositoryImpl implements GroupBuyingBoardLikeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    public GroupBuyingBoardLikeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    @Transactional
    public boolean isLiked(Member member, GroupBuyingBoard groupBuyingBoard) {
        QGroupBuyingBoardLike groupBuyingBoardLike = QGroupBuyingBoardLike.groupBuyingBoardLike;
        BooleanExpression isMember = groupBuyingBoardLike.member.eq(member);
        BooleanExpression isGroupBuying = groupBuyingBoardLike.groupBuyingBoard.eq(groupBuyingBoard);

        return queryFactory
                .select(groupBuyingBoardLike.member)
                .from(groupBuyingBoardLike)
                .where(isMember.and(isGroupBuying))
                .fetchFirst() != null;
    }

    @Override
    @Transactional
    public void like(Member member, GroupBuyingBoard groupBuyingBoard) {
        GroupBuyingBoardLike groupBuyingBoardLike = GroupBuyingBoardLike.builder()
                .member(member)
                .groupBuyingBoard(groupBuyingBoard).build();

        entityManager.persist(groupBuyingBoardLike);

        groupBuyingBoard.updateLikes();
    }

    @Override
    @Transactional
    public void unlike(Member member, GroupBuyingBoard groupBuyingBoard) {
        QGroupBuyingBoardLike groupBuyingBoardLike = QGroupBuyingBoardLike.groupBuyingBoardLike;
        BooleanExpression isMember = groupBuyingBoardLike.member.eq(member);
        BooleanExpression isGroupBuying = groupBuyingBoardLike.groupBuyingBoard.eq(groupBuyingBoard);

        queryFactory
                .delete(groupBuyingBoardLike)
                .where(isMember.and(isGroupBuying))
                .execute();

        groupBuyingBoard.updateLikes();
    }
}
