package com.ssafy.TogetherBuyMain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSeller is a Querydsl query type for Seller
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSeller extends EntityPathBase<Seller> {

    private static final long serialVersionUID = 836845062L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSeller seller = new QSeller("seller");

    public final StringPath bossName = createString("bossName");

    public final StringPath businessAddress = createString("businessAddress");

    public final StringPath businessEmail = createString("businessEmail");

    public final StringPath businessName = createString("businessName");

    public final StringPath businessNumber = createString("businessNumber");

    public final StringPath businessTel = createString("businessTel");

    public final QMember member;

    public final ListPath<com.ssafy.TogetherBuyMain.shop.entity.order.Order, com.ssafy.TogetherBuyMain.shop.entity.order.QOrder> orders = this.<com.ssafy.TogetherBuyMain.shop.entity.order.Order, com.ssafy.TogetherBuyMain.shop.entity.order.QOrder>createList("orders", com.ssafy.TogetherBuyMain.shop.entity.order.Order.class, com.ssafy.TogetherBuyMain.shop.entity.order.QOrder.class, PathInits.DIRECT2);

    public final ListPath<com.ssafy.TogetherBuyMain.shop.entity.product.Product, com.ssafy.TogetherBuyMain.shop.entity.product.QProduct> products = this.<com.ssafy.TogetherBuyMain.shop.entity.product.Product, com.ssafy.TogetherBuyMain.shop.entity.product.QProduct>createList("products", com.ssafy.TogetherBuyMain.shop.entity.product.Product.class, com.ssafy.TogetherBuyMain.shop.entity.product.QProduct.class, PathInits.DIRECT2);

    public final NumberPath<Long> sellerId = createNumber("sellerId", Long.class);

    public QSeller(String variable) {
        this(Seller.class, forVariable(variable), INITS);
    }

    public QSeller(Path<? extends Seller> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSeller(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSeller(PathMetadata metadata, PathInits inits) {
        this(Seller.class, metadata, inits);
    }

    public QSeller(Class<? extends Seller> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

