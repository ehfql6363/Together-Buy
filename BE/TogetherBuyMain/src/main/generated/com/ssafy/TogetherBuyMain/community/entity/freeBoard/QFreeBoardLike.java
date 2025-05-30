package com.ssafy.TogetherBuyMain.community.entity.freeBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFreeBoardLike is a Querydsl query type for FreeBoardLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFreeBoardLike extends EntityPathBase<FreeBoardLike> {

    private static final long serialVersionUID = 276735815L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFreeBoardLike freeBoardLike = new QFreeBoardLike("freeBoardLike");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final QFreeBoard freeBoard;

    public final NumberPath<Long> freeBoardLikeId = createNumber("freeBoardLikeId", Long.class);

    public final com.ssafy.TogetherBuyMain.member.entity.QMember member;

    public QFreeBoardLike(String variable) {
        this(FreeBoardLike.class, forVariable(variable), INITS);
    }

    public QFreeBoardLike(Path<? extends FreeBoardLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFreeBoardLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFreeBoardLike(PathMetadata metadata, PathInits inits) {
        this(FreeBoardLike.class, metadata, inits);
    }

    public QFreeBoardLike(Class<? extends FreeBoardLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.freeBoard = inits.isInitialized("freeBoard") ? new QFreeBoard(forProperty("freeBoard"), inits.get("freeBoard")) : null;
        this.member = inits.isInitialized("member") ? new com.ssafy.TogetherBuyMain.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

