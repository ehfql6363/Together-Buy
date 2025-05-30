package com.ssafy.TogetherBuyMain.billing.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPointChargeHistory is a Querydsl query type for PointChargeHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPointChargeHistory extends EntityPathBase<PointChargeHistory> {

    private static final long serialVersionUID = 1699702168L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPointChargeHistory pointChargeHistory = new QPointChargeHistory("pointChargeHistory");

    public final QAdminPoint adminPoint;

    public final NumberPath<Long> chargeAmount = createNumber("chargeAmount", Long.class);

    public final DateTimePath<java.time.LocalDateTime> chargeDateTime = createDateTime("chargeDateTime", java.time.LocalDateTime.class);

    public final EnumPath<ChargeStatus> chargeStatus = createEnum("chargeStatus", ChargeStatus.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.ssafy.TogetherBuyMain.member.entity.QMemberPoint memberPoint;

    public QPointChargeHistory(String variable) {
        this(PointChargeHistory.class, forVariable(variable), INITS);
    }

    public QPointChargeHistory(Path<? extends PointChargeHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPointChargeHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPointChargeHistory(PathMetadata metadata, PathInits inits) {
        this(PointChargeHistory.class, metadata, inits);
    }

    public QPointChargeHistory(Class<? extends PointChargeHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.adminPoint = inits.isInitialized("adminPoint") ? new QAdminPoint(forProperty("adminPoint")) : null;
        this.memberPoint = inits.isInitialized("memberPoint") ? new com.ssafy.TogetherBuyMain.member.entity.QMemberPoint(forProperty("memberPoint"), inits.get("memberPoint")) : null;
    }

}

