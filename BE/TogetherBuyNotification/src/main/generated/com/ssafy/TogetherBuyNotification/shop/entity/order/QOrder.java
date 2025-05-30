package com.ssafy.TogetherBuyNotification.shop.entity.order;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import com.ssafy.TogetherBuyNotification.chatting.entity.QChatRoom;
import com.ssafy.TogetherBuyNotification.member.entity.QMember;
import com.ssafy.TogetherBuyNotification.member.entity.QSeller;
import com.ssafy.TogetherBuyNotification.shop.entity.product.QProduct;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = -609057462L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final QChatRoom chatRoom;

    public final EnumPath<DeliveryStatus> deliveryStatus = createEnum("deliveryStatus", DeliveryStatus.class);

    public final QMember member;

    public final StringPath orderAddress = createString("orderAddress");

    public final DateTimePath<java.time.LocalDateTime> orderDate = createDateTime("orderDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final NumberPath<Long> orderPrice = createNumber("orderPrice", Long.class);

    public final NumberPath<Integer> orderQuantity = createNumber("orderQuantity", Integer.class);

    public final QProduct product;

    public final QSeller seller;

    public final StringPath wayBillNumber = createString("wayBillNumber");

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatRoom = inits.isInitialized("chatRoom") ? new QChatRoom(forProperty("chatRoom"), inits.get("chatRoom")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
        this.seller = inits.isInitialized("seller") ? new QSeller(forProperty("seller"), inits.get("seller")) : null;
    }

}

