package com.ssafy.TogetherBuyBilling.shop.entity.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QApply is a Querydsl query type for Apply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QApply extends EntityPathBase<Apply> {

    private static final long serialVersionUID = 1216155958L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QApply apply = new QApply("apply");

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final DateTimePath<java.time.LocalDateTime> appliedAt = createDateTime("appliedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> applyId = createNumber("applyId", Long.class);

    public final NumberPath<Long> cost = createNumber("cost", Long.class);

    public final QForm form;

    public final com.ssafy.TogetherBuyBilling.community.entity.groupBuyingBoard.QGroupBuyingBoard groupBuyingBoard;

    public final com.ssafy.TogetherBuyBilling.member.entity.QMember member;

    public final EnumPath<ApplyStatus> status = createEnum("status", ApplyStatus.class);

    public QApply(String variable) {
        this(Apply.class, forVariable(variable), INITS);
    }

    public QApply(Path<? extends Apply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QApply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QApply(PathMetadata metadata, PathInits inits) {
        this(Apply.class, metadata, inits);
    }

    public QApply(Class<? extends Apply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.form = inits.isInitialized("form") ? new QForm(forProperty("form"), inits.get("form")) : null;
        this.groupBuyingBoard = inits.isInitialized("groupBuyingBoard") ? new com.ssafy.TogetherBuyBilling.community.entity.groupBuyingBoard.QGroupBuyingBoard(forProperty("groupBuyingBoard"), inits.get("groupBuyingBoard")) : null;
        this.member = inits.isInitialized("member") ? new com.ssafy.TogetherBuyBilling.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

