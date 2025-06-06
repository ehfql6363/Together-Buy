package com.ssafy.TogetherBuyBilling.community.entity.comment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QComment is a Querydsl query type for Comment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComment extends EntityPathBase<Comment> {

    private static final long serialVersionUID = 2013226946L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QComment comment = new QComment("comment");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final com.ssafy.TogetherBuyBilling.community.entity.freeBoard.QFreeBoard freeBoard;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.ssafy.TogetherBuyBilling.member.entity.QMember member;

    public QComment(String variable) {
        this(Comment.class, forVariable(variable), INITS);
    }

    public QComment(Path<? extends Comment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QComment(PathMetadata metadata, PathInits inits) {
        this(Comment.class, metadata, inits);
    }

    public QComment(Class<? extends Comment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.freeBoard = inits.isInitialized("freeBoard") ? new com.ssafy.TogetherBuyBilling.community.entity.freeBoard.QFreeBoard(forProperty("freeBoard"), inits.get("freeBoard")) : null;
        this.member = inits.isInitialized("member") ? new com.ssafy.TogetherBuyBilling.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

