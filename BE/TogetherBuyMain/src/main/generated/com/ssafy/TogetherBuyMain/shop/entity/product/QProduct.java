package com.ssafy.TogetherBuyMain.shop.entity.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -2080450451L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final StringPath detailImage = createString("detailImage");

    public final NumberPath<Long> discountAmount = createNumber("discountAmount", Long.class);

    public final StringPath discountUnit = createString("discountUnit");

    public final ListPath<com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoard, com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.QGroupBuyingBoard> groupBuyingBoards = this.<com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoard, com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.QGroupBuyingBoard>createList("groupBuyingBoards", com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoard.class, com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.QGroupBuyingBoard.class, PathInits.DIRECT2);

    public final ListPath<com.ssafy.TogetherBuyMain.shop.entity.common.ProductImage, com.ssafy.TogetherBuyMain.shop.entity.common.QProductImage> images = this.<com.ssafy.TogetherBuyMain.shop.entity.common.ProductImage, com.ssafy.TogetherBuyMain.shop.entity.common.QProductImage>createList("images", com.ssafy.TogetherBuyMain.shop.entity.common.ProductImage.class, com.ssafy.TogetherBuyMain.shop.entity.common.QProductImage.class, PathInits.DIRECT2);

    public final ListPath<com.ssafy.TogetherBuyMain.shop.entity.order.Order, com.ssafy.TogetherBuyMain.shop.entity.order.QOrder> orders = this.<com.ssafy.TogetherBuyMain.shop.entity.order.Order, com.ssafy.TogetherBuyMain.shop.entity.order.QOrder>createList("orders", com.ssafy.TogetherBuyMain.shop.entity.order.Order.class, com.ssafy.TogetherBuyMain.shop.entity.order.QOrder.class, PathInits.DIRECT2);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final NumberPath<Long> pricePerUnit = createNumber("pricePerUnit", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final StringPath productName = createString("productName");

    public final NumberPath<Long> salePrice = createNumber("salePrice", Long.class);

    public final com.ssafy.TogetherBuyMain.member.entity.QSeller seller;

    public final QSubCategory subCategory;

    public final NumberPath<Long> total = createNumber("total", Long.class);

    public final NumberPath<Long> unitAmount = createNumber("unitAmount", Long.class);

    public final StringPath unitName = createString("unitName");

    public final SetPath<com.ssafy.TogetherBuyMain.member.entity.Member, com.ssafy.TogetherBuyMain.member.entity.QMember> wishingMembers = this.<com.ssafy.TogetherBuyMain.member.entity.Member, com.ssafy.TogetherBuyMain.member.entity.QMember>createSet("wishingMembers", com.ssafy.TogetherBuyMain.member.entity.Member.class, com.ssafy.TogetherBuyMain.member.entity.QMember.class, PathInits.DIRECT2);

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.seller = inits.isInitialized("seller") ? new com.ssafy.TogetherBuyMain.member.entity.QSeller(forProperty("seller"), inits.get("seller")) : null;
        this.subCategory = inits.isInitialized("subCategory") ? new QSubCategory(forProperty("subCategory"), inits.get("subCategory")) : null;
    }

}

