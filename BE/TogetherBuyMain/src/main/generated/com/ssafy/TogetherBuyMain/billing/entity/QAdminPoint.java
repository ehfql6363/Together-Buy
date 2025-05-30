package com.ssafy.TogetherBuyMain.billing.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdminPoint is a Querydsl query type for AdminPoint
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdminPoint extends EntityPathBase<AdminPoint> {

    private static final long serialVersionUID = 1587346825L;

    public static final QAdminPoint adminPoint = new QAdminPoint("adminPoint");

    public final NumberPath<Long> point = createNumber("point", Long.class);

    public final ListPath<PointChargeHistory, QPointChargeHistory> pointChargeHistories = this.<PointChargeHistory, QPointChargeHistory>createList("pointChargeHistories", PointChargeHistory.class, QPointChargeHistory.class, PathInits.DIRECT2);

    public final NumberPath<Long> pointId = createNumber("pointId", Long.class);

    public QAdminPoint(String variable) {
        super(AdminPoint.class, forVariable(variable));
    }

    public QAdminPoint(Path<? extends AdminPoint> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdminPoint(PathMetadata metadata) {
        super(AdminPoint.class, metadata);
    }

}

