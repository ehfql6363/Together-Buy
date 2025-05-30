package com.ssafy.TogetherBuyNotification.shop.entity.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import com.ssafy.TogetherBuyNotification.community.entity.groupBuyingBoard.GroupBuyingBoard;
import com.ssafy.TogetherBuyNotification.community.entity.groupBuyingBoard.QGroupBuyingBoard;
import com.ssafy.TogetherBuyNotification.member.entity.Member;
import com.ssafy.TogetherBuyNotification.member.entity.QMember;
import com.ssafy.TogetherBuyNotification.member.entity.QSeller;
import com.ssafy.TogetherBuyNotification.shop.entity.common.ProductImage;
import com.ssafy.TogetherBuyNotification.shop.entity.common.QProductImage;
import com.ssafy.TogetherBuyNotification.shop.entity.order.Order;
import com.ssafy.TogetherBuyNotification.shop.entity.order.QOrder;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -1225535092L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final StringPath detailImage = createString("detailImage");

    public final NumberPath<Long> discountAmount = createNumber("discountAmount", Long.class);

    public final StringPath discountUnit = createString("discountUnit");

    public final ListPath<GroupBuyingBoard, QGroupBuyingBoard> groupBuyingBoards = this.<GroupBuyingBoard, QGroupBuyingBoard>createList("groupBuyingBoards", GroupBuyingBoard.class, QGroupBuyingBoard.class, PathInits.DIRECT2);

    public final ListPath<ProductImage, QProductImage> images = this.<ProductImage, QProductImage>createList("images", ProductImage.class, QProductImage.class, PathInits.DIRECT2);

    public final ListPath<Order, QOrder> orders = this.<Order, QOrder>createList("orders", Order.class, QOrder.class, PathInits.DIRECT2);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final NumberPath<Long> pricePerUnit = createNumber("pricePerUnit", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final StringPath productName = createString("productName");

    public final NumberPath<Long> salePrice = createNumber("salePrice", Long.class);

    public final QSeller seller;

    public final QSubCategory subCategory;

    public final NumberPath<Long> total = createNumber("total", Long.class);

    public final NumberPath<Long> unitAmount = createNumber("unitAmount", Long.class);

    public final StringPath unitName = createString("unitName");

    public final SetPath<Member, QMember> wishingMembers = this.<Member, QMember>createSet("wishingMembers", Member.class, QMember.class, PathInits.DIRECT2);

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
        this.seller = inits.isInitialized("seller") ? new QSeller(forProperty("seller"), inits.get("seller")) : null;
        this.subCategory = inits.isInitialized("subCategory") ? new QSubCategory(forProperty("subCategory"), inits.get("subCategory")) : null;
    }

}

