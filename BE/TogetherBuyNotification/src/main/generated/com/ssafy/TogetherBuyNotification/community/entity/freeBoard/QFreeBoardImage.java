package com.ssafy.TogetherBuyNotification.community.entity.freeBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFreeBoardImage is a Querydsl query type for FreeBoardImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFreeBoardImage extends EntityPathBase<FreeBoardImage> {

    private static final long serialVersionUID = 1124908682L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFreeBoardImage freeBoardImage = new QFreeBoardImage("freeBoardImage");

    public final StringPath contentType = createString("contentType");

    public final StringPath fileName = createString("fileName");

    public final QFreeBoard freeBoard;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath url = createString("url");

    public QFreeBoardImage(String variable) {
        this(FreeBoardImage.class, forVariable(variable), INITS);
    }

    public QFreeBoardImage(Path<? extends FreeBoardImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFreeBoardImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFreeBoardImage(PathMetadata metadata, PathInits inits) {
        this(FreeBoardImage.class, metadata, inits);
    }

    public QFreeBoardImage(Class<? extends FreeBoardImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.freeBoard = inits.isInitialized("freeBoard") ? new QFreeBoard(forProperty("freeBoard"), inits.get("freeBoard")) : null;
    }

}

