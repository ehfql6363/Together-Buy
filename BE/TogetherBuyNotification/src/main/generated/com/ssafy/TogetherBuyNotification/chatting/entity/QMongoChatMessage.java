package com.ssafy.TogetherBuyNotification.chatting.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMongoChatMessage is a Querydsl query type for MongoChatMessage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMongoChatMessage extends EntityPathBase<MongoChatMessage> {

    private static final long serialVersionUID = 2021401519L;

    public static final QMongoChatMessage mongoChatMessage = new QMongoChatMessage("mongoChatMessage");

    public final NumberPath<Long> chatRoomId = createNumber("chatRoomId", Long.class);

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> senderId = createNumber("senderId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> timestamp = createDateTime("timestamp", java.time.LocalDateTime.class);

    public QMongoChatMessage(String variable) {
        super(MongoChatMessage.class, forVariable(variable));
    }

    public QMongoChatMessage(Path<? extends MongoChatMessage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMongoChatMessage(PathMetadata metadata) {
        super(MongoChatMessage.class, metadata);
    }

}

