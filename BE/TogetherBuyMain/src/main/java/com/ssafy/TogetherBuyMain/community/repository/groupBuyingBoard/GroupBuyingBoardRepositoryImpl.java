package com.ssafy.TogetherBuyMain.community.repository.groupBuyingBoard;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.TogetherBuyMain.community.dto.groupBuyingBoard.ExpiringGroupBuyingBoardDTO;
import com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.QGroupBuyingBoard;
import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.member.entity.QMemberLocation;
import com.ssafy.TogetherBuyMain.shop.entity.common.QForm;
import com.ssafy.TogetherBuyMain.shop.entity.common.QMeetingLocation;
import com.ssafy.TogetherBuyMain.shop.entity.product.QProduct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class GroupBuyingBoardRepositoryImpl implements GroupBuyingBoardRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    private final JPAQueryFactory queryFactory;

    public GroupBuyingBoardRepositoryImpl(EntityManager entityManager) {
        this.em = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<ExpiringGroupBuyingBoardDTO> findExpiringGroupBuyingBoardByMemberLocation(Member member) {
        QGroupBuyingBoard groupBuyingBoard = QGroupBuyingBoard.groupBuyingBoard;
        QForm form = QForm.form;
        QProduct product = QProduct.product;
        QMemberLocation memberLocation = QMemberLocation.memberLocation;

        return queryFactory
                .select(Projections.constructor(
                        ExpiringGroupBuyingBoardDTO.class,
                        groupBuyingBoard.groupBuyingBoardId,
                        groupBuyingBoard.title,
                        form.currentAmount,
                        product.total,
                        getFullAddress(form.meetingLocation),
                        form.startTime,
                        form.endTime,
                        Expressions.stringTemplate(
                                "CASE WHEN SIZE({0}) = 0 THEN NULL ELSE (SELECT i.url FROM {0} i LIMIT 1) END",
                                product.images
                        )
                ))
                .from(groupBuyingBoard)
                .join(groupBuyingBoard.form, form)
                .join(groupBuyingBoard.product, product)
                .join(memberLocation).on(memberLocation.member.eq(member))
                .where(
                        isSameLocation(memberLocation, form)
                                .and(form.currentAmount.multiply(100).divide(product.total).goe(80))
                                .and(product.total.gt(0))
                )
                .fetch();
    }

    private BooleanExpression isSameLocation(QMemberLocation memberLocation, QForm form) {
        QMeetingLocation meetingLocation = form.meetingLocation;
        BooleanExpression isSameSido = memberLocation.sido.eq(meetingLocation.sido);
        BooleanExpression isSameSigungu = memberLocation.sigungu.eq(meetingLocation.sigungu);
        return isSameSido.and(isSameSigungu);
    }

    public Expression<String> getFullAddress(QMeetingLocation meetingLocation) {
        return Expressions.stringTemplate(
                "CONCAT(COALESCE({0}, ''), ' ', COALESCE({1}, ''), ' ', COALESCE({2}, ''), ' ', COALESCE({3}, ''), ' ', COALESCE({4}, ''), ' ', COALESCE({5}, ''))",
                meetingLocation.sido,
                meetingLocation.sigungu,
                meetingLocation.eupmyeondong,
                meetingLocation.ri,
                meetingLocation.loadName,
                meetingLocation.loadNumber
        );
    }

    private Expression<String> getFirstImage(Long productId) {
        String imageUrl = new JPAQuery<String>(em)
                .select(QProduct.product.images.any().url)
                .from(QProduct.product)
                .where(QProduct.product.productId.eq(productId))
                .limit(1)
                .fetchOne();

        return Expressions.asString(imageUrl != null ? imageUrl : "");  // NULL 방지
    }

}
