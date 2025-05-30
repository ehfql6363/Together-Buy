package com.ssafy.TogetherBuyBilling.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPoint is a Querydsl query type for Point
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPoint extends EntityPathBase<Point> {

    private static final long serialVersionUID = 1826780417L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPoint point = new QPoint("point");

    public final NumberPath<Long> balance = createNumber("balance", Long.class);

    public final ListPath<com.ssafy.TogetherBuyBilling.billing.entity.PointChargeHistory, com.ssafy.TogetherBuyBilling.billing.entity.QPointChargeHistory> chargeHistories = this.<com.ssafy.TogetherBuyBilling.billing.entity.PointChargeHistory, com.ssafy.TogetherBuyBilling.billing.entity.QPointChargeHistory>createList("chargeHistories", com.ssafy.TogetherBuyBilling.billing.entity.PointChargeHistory.class, com.ssafy.TogetherBuyBilling.billing.entity.QPointChargeHistory.class, PathInits.DIRECT2);

    public final QMember member;

    public final NumberPath<Long> pointId = createNumber("pointId", Long.class);

    public QPoint(String variable) {
        this(Point.class, forVariable(variable), INITS);
    }

    public QPoint(Path<? extends Point> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPoint(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPoint(PathMetadata metadata, PathInits inits) {
        this(Point.class, metadata, inits);
    }

    public QPoint(Class<? extends Point> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

