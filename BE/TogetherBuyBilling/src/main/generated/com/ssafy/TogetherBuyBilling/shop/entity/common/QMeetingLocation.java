package com.ssafy.TogetherBuyBilling.shop.entity.common;

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

    private static final long serialVersionUID = 1845286616L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMeetingLocation meetingLocation = new QMeetingLocation("meetingLocation");

    public final QForm form;

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath meetingAddress = createString("meetingAddress");

    public final NumberPath<Long> meetingLocationId = createNumber("meetingLocationId", Long.class);

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

