package com.ssafy.TogetherBuyNotification.chatting.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import com.ssafy.TogetherBuyNotification.community.entity.groupBuyingBoard.QGroupBuyingBoard;
import com.ssafy.TogetherBuyNotification.member.entity.Member;
import com.ssafy.TogetherBuyNotification.member.entity.QMember;
import com.ssafy.TogetherBuyNotification.shop.entity.order.QOrder;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoom is a Querydsl query type for ChatRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoom extends EntityPathBase<ChatRoom> {

    private static final long serialVersionUID = -1673200633L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatRoom chatRoom = new QChatRoom("chatRoom");

    public final NumberPath<Long> chatRoomId = createNumber("chatRoomId", Long.class);

    public final QMember creator;

    public final QGroupBuyingBoard groupBuyingBoard;

    public final QOrder order;

    public final SetPath<Member, QMember> participants = this.<Member, QMember>createSet("participants", Member.class, QMember.class, PathInits.DIRECT2);

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
        this.creator = inits.isInitialized("creator") ? new QMember(forProperty("creator"), inits.get("creator")) : null;
        this.groupBuyingBoard = inits.isInitialized("groupBuyingBoard") ? new QGroupBuyingBoard(forProperty("groupBuyingBoard"), inits.get("groupBuyingBoard")) : null;
        this.order = inits.isInitialized("order") ? new QOrder(forProperty("order"), inits.get("order")) : null;
    }

}

