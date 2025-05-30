package com.ssafy.TogetherBuyMain.shop.entity.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMeetingLocation is a Querydsl query type for MeetingLocation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMeetingLocation extends EntityPathBase<MeetingLocation> {

    private static final long serialVersionUID = -614572880L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMeetingLocation meetingLocation = new QMeetingLocation("meetingLocation");

    public final StringPath eupmyeondong = createString("eupmyeondong");

    public final QForm form;

    public final StringPath loadName = createString("loadName");

    public final StringPath loadNumber = createString("loadNumber");

    public final NumberPath<Long> meetingLocationId = createNumber("meetingLocationId", Long.class);

    public final StringPath ri = createString("ri");

    public final StringPath sido = createString("sido");

    public final StringPath sigungu = createString("sigungu");

    public QMeetingLocation(String variable) {
        this(MeetingLocation.class, forVariable(variable), INITS);
    }

    public QMeetingLocation(Path<? extends MeetingLocation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMeetingLocation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMeetingLocation(PathMetadata metadata, PathInits inits) {
        this(MeetingLocation.class, metadata, inits);
    }

    public QMeetingLocation(Class<? extends MeetingLocation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.form = inits.isInitialized("form") ? new QForm(forProperty("form"), inits.get("form")) : null;
    }

}

