package com.ssafy.TogetherBuyMain.chatting.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoom is a Querydsl query type for ChatRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoom extends EntityPathBase<ChatRoom> {

    private static final long serialVersionUID = 1662863558L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatRoom chatRoom = new QChatRoom("chatRoom");

    public final NumberPath<Long> chatRoomId = createNumber("chatRoomId", Long.class);

    public final com.ssafy.TogetherBuyMain.member.entity.QMember creator;

    public final com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.QGroupBuyingBoard groupBuyingBoard;

    public final com.ssafy.TogetherBuyMain.shop.entity.order.QOrder order;

    public final SetPath<com.ssafy.TogetherBuyMain.member.entity.Member, com.ssafy.TogetherBuyMain.member.entity.QMember> participants = this.<com.ssafy.TogetherBuyMain.member.entity.Member, com.ssafy.TogetherBuyMain.member.entity.QMember>createSet("participants", com.ssafy.TogetherBuyMain.member.entity.Member.class, com.ssafy.TogetherBuyMain.member.entity.QMember.class, PathInits.DIRECT2);

    public QChatRoom(String variable) {
        this(ChatRoom.class, forVariable(variable), INITS);
    }

    public QChatRoom(Path<? extends ChatRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatRoom(PathMetadata metadata, PathInits inits) {
        this(ChatRoom.class, metadata, inits);
    }

    public QChatRoom(Class<? extends ChatRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.creator = inits.isInitialized("creator") ? new com.ssafy.TogetherBuyMain.member.entity.QMember(forProperty("creator"), inits.get("creator")) : null;
        this.groupBuyingBoard = inits.isInitialized("groupBuyingBoard") ? new com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.QGroupBuyingBoard(forProperty("groupBuyingBoard"), inits.get("groupBuyingBoard")) : null;
        this.order = inits.isInitialized("order") ? new com.ssafy.TogetherBuyMain.shop.entity.order.QOrder(forProperty("order"), inits.get("order")) : null;
    }

}

