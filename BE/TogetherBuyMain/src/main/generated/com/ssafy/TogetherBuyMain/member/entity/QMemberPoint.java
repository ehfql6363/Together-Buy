package com.ssafy.TogetherBuyMain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberPoint is a Querydsl query type for MemberPoint
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberPoint extends EntityPathBase<MemberPoint> {

    private static final long serialVersionUID = -1238730513L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberPoint memberPoint = new QMemberPoint("memberPoint");

    public final NumberPath<Long> balance = createNumber("balance", Long.class);

    public final StringPath billingKey = createString("billingKey");

    public final ListPath<com.ssafy.TogetherBuyMain.billing.entity.PointChargeHistory, com.ssafy.TogetherBuyMain.billing.entity.QPointChargeHistory> chargeHistories = this.<com.ssafy.TogetherBuyMain.billing.entity.PointChargeHistory, com.ssafy.TogetherBuyMain.billing.entity.QPointChargeHistory>createList("chargeHistories", com.ssafy.TogetherBuyMain.billing.entity.PointChargeHistory.class, com.ssafy.TogetherBuyMain.billing.entity.QPointChargeHistory.class, PathInits.DIRECT2);

    public final QMember member;

    public final NumberPath<Long> memberPointId = createNumber("memberPointId", Long.class);

    public QMemberPoint(String variable) {
        this(MemberPoint.class, forVariable(variable), INITS);
    }

    public QMemberPoint(Path<? extends MemberPoint> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberPoint(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberPoint(PathMetadata metadata, PathInits inits) {
        this(MemberPoint.class, metadata, inits);
    }

    public QMemberPoint(Class<? extends MemberPoint> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

