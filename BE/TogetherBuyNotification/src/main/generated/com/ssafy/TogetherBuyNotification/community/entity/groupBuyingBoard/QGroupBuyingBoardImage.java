package com.ssafy.TogetherBuyNotification.community.entity.groupBuyingBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroupBuyingBoardImage is a Querydsl query type for GroupBuyingBoardImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupBuyingBoardImage extends EntityPathBase<GroupBuyingBoardImage> {

    private static final long serialVersionUID = 1016918466L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroupBuyingBoardImage groupBuyingBoardImage = new QGroupBuyingBoardImage("groupBuyingBoardImage");

    public final StringPath contentType = createString("contentType");

    public final StringPath fileName = createString("fileName");

    public final QGroupBuyingBoard groupBuyingBoard;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath url = createString("url");

    public QGroupBuyingBoardImage(String variable) {
        this(GroupBuyingBoardImage.class, forVariable(variable), INITS);
    }

    public QGroupBuyingBoardImage(Path<? extends GroupBuyingBoardImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroupBuyingBoardImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroupBuyingBoardImage(PathMetadata metadata, PathInits inits) {
        this(GroupBuyingBoardImage.class, metadata, inits);
    }

    public QGroupBuyingBoardImage(Class<? extends GroupBuyingBoardImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.groupBuyingBoard = inits.isInitialized("groupBuyingBoard") ? new QGroupBuyingBoard(forProperty("groupBuyingBoard"), inits.get("groupBuyingBoard")) : null;
    }

}

