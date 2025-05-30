package com.ssafy.TogetherBuyBilling.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.TogetherBuyBilling.member.entity.Member;
import com.ssafy.TogetherBuyBilling.member.entity.QMember;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager entityManager) {
        this.em = entityManager;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Member findByEmail(String email) {
        QMember member = QMember.member;

        return queryFactory.selectFrom(member)
                .where(member.email.eq(email))
                .fetchOne();
    }
}
