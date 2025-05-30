package com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroupBuyingBoard is a Querydsl query type for GroupBuyingBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupBuyingBoard extends EntityPathBase<GroupBuyingBoard> {

    private static final long serialVersionUID = -58057960L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroupBuyingBoard groupBuyingBoard = new QGroupBuyingBoard("groupBuyingBoard");

    public final com.ssafy.TogetherBuyMain.chatting.entity.QChatRoom chatRoom;

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final com.ssafy.TogetherBuyMain.member.entity.QMember creator;

    public final com.ssafy.TogetherBuyMain.shop.entity.common.QForm form;

    public final NumberPath<Long> groupBuyingBoardId = createNumber("groupBuyingBoardId", Long.class);

    public final ListPath<GroupBuyingBoardLike, QGroupBuyingBoardLike> groupBuyingBoardLikes = this.<GroupBuyingBoardLike, QGroupBuyingBoardLike>createList("groupBuyingBoardLikes", GroupBuyingBoardLike.class, QGroupBuyingBoardLike.class, PathInits.DIRECT2);

    public final ListPath<GroupBuyingBoardImage, QGroupBuyingBoardImage> images = this.<GroupBuyingBoardImage, QGroupBuyingBoardImage>createList("images", GroupBuyingBoardImage.class, QGroupBuyingBoardImage.class, PathInits.DIRECT2);

    public final NumberPath<Long> likes = createNumber("likes", Long.class);

    public final com.ssafy.TogetherBuyMain.shop.entity.product.QProduct product;

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> views = createNumber("views", Long.class);

    public QGroupBuyingBoard(String variable) {
        this(GroupBuyingBoard.class, forVariable(variable), INITS);
    }

    public QGroupBuyingBoard(Path<? extends GroupBuyingBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroupBuyingBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroupBuyingBoard(PathMetadata metadata, PathInits inits) {
        this(GroupBuyingBoard.class, metadata, inits);
    }

    public QGroupBuyingBoard(Class<? extends GroupBuyingBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatRoom = inits.isInitialized("chatRoom") ? new com.ssafy.TogetherBuyMain.chatting.entity.QChatRoom(forProperty("chatRoom"), inits.get("chatRoom")) : null;
        this.creator = inits.isInitialized("creator") ? new com.ssafy.TogetherBuyMain.member.entity.QMember(forProperty("creator"), inits.get("creator")) : null;
        this.form = inits.isInitialized("form") ? new com.ssafy.TogetherBuyMain.shop.entity.common.QForm(forProperty("form"), inits.get("form")) : null;
        this.product = inits.isInitialized("product") ? new com.ssafy.TogetherBuyMain.shop.entity.product.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

