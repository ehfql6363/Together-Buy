package com.ssafy.TogetherBuyMain.shop.repository.product;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.member.entity.QMember;
import com.ssafy.TogetherBuyMain.shop.dto.product.WishedProductDTO;
import com.ssafy.TogetherBuyMain.shop.entity.common.QProductImage;
import com.ssafy.TogetherBuyMain.shop.entity.product.QProduct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(EntityManager entityManager) {
        this.em = entityManager;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WishedProductDTO> findAllByWishMember(Member member) {
        QProduct product = QProduct.product;
        QProductImage image = QProductImage.productImage;
        QMember wishMember = QMember.member;

        return queryFactory.select(Projections.constructor(
                        WishedProductDTO.class,
                        product.productId,
                        product.productName,
                        image.url,
                        product.pricePerUnit,       // unitPrice
                        product.salePrice,       // salePrice
                        product.unitName,        // unitName
                        product.seller.businessName // businessName
                ))
                .from(product)
                .join(product.wishingMembers, wishMember)
                .join(product.images, image)
                .where(isSameMember(member, wishMember))
                .fetch();
    }

    private BooleanExpression isSameMember(Member member, QMember qMember) {
        Long memberId = member.getMemberId();
        return qMember.memberId.eq(memberId);
    }
}
