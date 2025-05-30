package com.ssafy.TogetherBuyMain.security.oauth.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOAuth2Attribute is a Querydsl query type for OAuth2Attribute
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOAuth2Attribute extends EntityPathBase<OAuth2Attribute> {

    private static final long serialVersionUID = -325671637L;

    public static final QOAuth2Attribute oAuth2Attribute = new QOAuth2Attribute("oAuth2Attribute");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath provider = createString("provider");

    public final StringPath providerId = createString("providerId");

    public QOAuth2Attribute(String variable) {
        super(OAuth2Attribute.class, forVariable(variable));
    }

    public QOAuth2Attribute(Path<? extends OAuth2Attribute> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOAuth2Attribute(PathMetadata metadata) {
        super(OAuth2Attribute.class, metadata);
    }

}

