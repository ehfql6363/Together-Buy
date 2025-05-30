package com.ssafy.TogetherBuyMain.shop.repository.common;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.shop.entity.common.Apply;
import com.ssafy.TogetherBuyMain.shop.entity.common.QApply;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ApplyRepositoryImpl implements ApplyRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    private final JPAQueryFactory queryFactory;

    public ApplyRepositoryImpl(EntityManager entityManager) {
        this.em = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<Apply> findAllByMember(Member member) {
        QApply apply = QApply.apply;
        return queryFactory.selectFrom(apply)
                .where(apply.member.eq(member))
                .fetch();
    }
}
