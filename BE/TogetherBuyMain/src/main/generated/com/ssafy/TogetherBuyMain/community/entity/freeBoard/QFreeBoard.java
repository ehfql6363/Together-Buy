package com.ssafy.TogetherBuyMain.community.entity.freeBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFreeBoard is a Querydsl query type for FreeBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFreeBoard extends EntityPathBase<FreeBoard> {

    private static final long serialVersionUID = 1355467664L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFreeBoard freeBoard = new QFreeBoard("freeBoard");

    public final ListPath<com.ssafy.TogetherBuyMain.community.entity.comment.Comment, com.ssafy.TogetherBuyMain.community.entity.comment.QComment> comments = this.<com.ssafy.TogetherBuyMain.community.entity.comment.Comment, com.ssafy.TogetherBuyMain.community.entity.comment.QComment>createList("comments", com.ssafy.TogetherBuyMain.community.entity.comment.Comment.class, com.ssafy.TogetherBuyMain.community.entity.comment.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final ListPath<FreeBoardLike, QFreeBoardLike> freeBoardLikes = this.<FreeBoardLike, QFreeBoardLike>createList("freeBoardLikes", FreeBoardLike.class, QFreeBoardLike.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<FreeBoardImage, QFreeBoardImage> images = this.<FreeBoardImage, QFreeBoardImage>createList("images", FreeBoardImage.class, QFreeBoardImage.class, PathInits.DIRECT2);

    public final NumberPath<Long> likes = createNumber("likes", Long.class);

    public final com.ssafy.TogetherBuyMain.member.entity.QMember member;

    public final com.ssafy.TogetherBuyMain.shop.entity.product.QProduct product;

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> views = createNumber("views", Long.class);

    public QFreeBoard(String variable) {
        this(FreeBoard.class, forVariable(variable), INITS);
    }

    public QFreeBoard(Path<? extends FreeBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFreeBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFreeBoard(PathMetadata metadata, PathInits inits) {
        this(FreeBoard.class, metadata, inits);
    }

    public QFreeBoard(Class<? extends FreeBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.ssafy.TogetherBuyMain.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
        this.product = inits.isInitialized("product") ? new com.ssafy.TogetherBuyMain.shop.entity.product.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

