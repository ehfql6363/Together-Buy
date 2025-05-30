package com.ssafy.TogetherBuyBilling.community.entity.groupBuyingBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroupBuyingBoardLike is a Querydsl query type for GroupBuyingBoardLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupBuyingBoardLike extends EntityPathBase<GroupBuyingBoardLike> {

    private static final long serialVersionUID = 436127991L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroupBuyingBoardLike groupBuyingBoardLike = new QGroupBuyingBoardLike("groupBuyingBoardLike");

    public final QGroupBuyingBoard groupBuyingBoard;

    public final NumberPath<Long> groupBuyingLikeId = createNumber("groupBuyingLikeId", Long.class);

    public final com.ssafy.TogetherBuyBilling.member.entity.QMember member;

    public QGroupBuyingBoardLike(String variable) {
        this(GroupBuyingBoardLike.class, forVariable(variable), INITS);
    }

    public QGroupBuyingBoardLike(Path<? extends GroupBuyingBoardLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroupBuyingBoardLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroupBuyingBoardLike(PathMetadata metadata, PathInits inits) {
        this(GroupBuyingBoardLike.class, metadata, inits);
    }

    public QGroupBuyingBoardLike(Class<? extends GroupBuyingBoardLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.groupBuyingBoard = inits.isInitialized("groupBuyingBoard") ? new QGroupBuyingBoard(forProperty("groupBuyingBoard"), inits.get("groupBuyingBoard")) : null;
        this.member = inits.isInitialized("member") ? new com.ssafy.TogetherBuyBilling.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

