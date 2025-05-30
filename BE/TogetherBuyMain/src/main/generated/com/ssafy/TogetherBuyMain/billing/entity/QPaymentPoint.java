package com.ssafy.TogetherBuyMain.billing.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPaymentPoint is a Querydsl query type for PaymentPoint
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaymentPoint extends EntityPathBase<PaymentPoint> {

    private static final long serialVersionUID = 935986994L;

    public static final QPaymentPoint paymentPoint = new QPaymentPoint("paymentPoint");

    public final NumberPath<Long> cost = createNumber("cost", Long.class);

    public final NumberPath<Long> point = createNumber("point", Long.class);

    public final NumberPath<Long> pointId = createNumber("pointId", Long.class);

    public QPaymentPoint(String variable) {
        super(PaymentPoint.class, forVariable(variable));
    }

    public QPaymentPoint(Path<? extends PaymentPoint> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPaymentPoint(PathMetadata metadata) {
        super(PaymentPoint.class, metadata);
    }

}

