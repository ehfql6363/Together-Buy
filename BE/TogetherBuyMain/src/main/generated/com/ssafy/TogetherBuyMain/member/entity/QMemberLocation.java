package com.ssafy.TogetherBuyMain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberLocation is a Querydsl query type for MemberLocation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberLocation extends EntityPathBase<MemberLocation> {

    private static final long serialVersionUID = 773311478L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberLocation memberLocation = new QMemberLocation("memberLocation");

    public final StringPath detailAddress = createString("detailAddress");

    public final StringPath eupmyeondong = createString("eupmyeondong");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final StringPath ri = createString("ri");

    public final StringPath roadName = createString("roadName");

    public final StringPath roadNumber = createString("roadNumber");

    public final StringPath sido = createString("sido");

    public final StringPath sigungu = createString("sigungu");

    public QMemberLocation(String variable) {
        this(MemberLocation.class, forVariable(variable), INITS);
    }

    public QMemberLocation(Path<? extends MemberLocation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberLocation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberLocation(PathMetadata metadata, PathInits inits) {
        this(MemberLocation.class, metadata, inits);
    }

    public QMemberLocation(Class<? extends MemberLocation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

