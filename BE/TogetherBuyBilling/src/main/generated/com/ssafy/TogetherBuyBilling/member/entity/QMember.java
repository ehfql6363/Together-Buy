package com.ssafy.TogetherBuyBilling.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 700602697L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final StringPath account = createString("account");

    public final ListPath<com.ssafy.TogetherBuyBilling.shop.entity.common.Apply, com.ssafy.TogetherBuyBilling.shop.entity.common.QApply> applies = this.<com.ssafy.TogetherBuyBilling.shop.entity.common.Apply, com.ssafy.TogetherBuyBilling.shop.entity.common.QApply>createList("applies", com.ssafy.TogetherBuyBilling.shop.entity.common.Apply.class, com.ssafy.TogetherBuyBilling.shop.entity.common.QApply.class, PathInits.DIRECT2);

    public final StringPath bank = createString("bank");

    public final DateTimePath<java.time.LocalDateTime> birth = createDateTime("birth", java.time.LocalDateTime.class);

    public final ListPath<com.ssafy.TogetherBuyBilling.chatting.entity.ChatRoom, com.ssafy.TogetherBuyBilling.chatting.entity.QChatRoom> chatRooms = this.<com.ssafy.TogetherBuyBilling.chatting.entity.ChatRoom, com.ssafy.TogetherBuyBilling.chatting.entity.QChatRoom>createList("chatRooms", com.ssafy.TogetherBuyBilling.chatting.entity.ChatRoom.class, com.ssafy.TogetherBuyBilling.chatting.entity.QChatRoom.class, PathInits.DIRECT2);

    public final ListPath<com.ssafy.TogetherBuyBilling.community.entity.comment.Comment, com.ssafy.TogetherBuyBilling.community.entity.comment.QComment> comments = this.<com.ssafy.TogetherBuyBilling.community.entity.comment.Comment, com.ssafy.TogetherBuyBilling.community.entity.comment.QComment>createList("comments", com.ssafy.TogetherBuyBilling.community.entity.comment.Comment.class, com.ssafy.TogetherBuyBilling.community.entity.comment.QComment.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final ListPath<com.ssafy.TogetherBuyBilling.community.entity.freeBoard.FreeBoardLike, com.ssafy.TogetherBuyBilling.community.entity.freeBoard.QFreeBoardLike> freeBoardLikes = this.<com.ssafy.TogetherBuyBilling.community.entity.freeBoard.FreeBoardLike, com.ssafy.TogetherBuyBilling.community.entity.freeBoard.QFreeBoardLike>createList("freeBoardLikes", com.ssafy.TogetherBuyBilling.community.entity.freeBoard.FreeBoardLike.class, com.ssafy.TogetherBuyBilling.community.entity.freeBoard.QFreeBoardLike.class, PathInits.DIRECT2);

    public final ListPath<com.ssafy.TogetherBuyBilling.community.entity.freeBoard.FreeBoard, com.ssafy.TogetherBuyBilling.community.entity.freeBoard.QFreeBoard> freeBoards = this.<com.ssafy.TogetherBuyBilling.community.entity.freeBoard.FreeBoard, com.ssafy.TogetherBuyBilling.community.entity.freeBoard.QFreeBoard>createList("freeBoards", com.ssafy.TogetherBuyBilling.community.entity.freeBoard.FreeBoard.class, com.ssafy.TogetherBuyBilling.community.entity.freeBoard.QFreeBoard.class, PathInits.DIRECT2);

    public final NumberPath<Long> gender = createNumber("gender", Long.class);

    public final ListPath<com.ssafy.TogetherBuyBilling.community.entity.groupBuyingBoard.GroupBuyingBoardLike, com.ssafy.TogetherBuyBilling.community.entity.groupBuyingBoard.QGroupBuyingBoardLike> groupBuyingBoardLikes = this.<com.ssafy.TogetherBuyBilling.community.entity.groupBuyingBoard.GroupBuyingBoardLike, com.ssafy.TogetherBuyBilling.community.entity.groupBuyingBoard.QGroupBuyingBoardLike>createList("groupBuyingBoardLikes", com.ssafy.TogetherBuyBilling.community.entity.groupBuyingBoard.GroupBuyingBoardLike.class, com.ssafy.TogetherBuyBilling.community.entity.groupBuyingBoard.QGroupBuyingBoardLike.class, PathInits.DIRECT2);

    public final ListPath<com.ssafy.TogetherBuyBilling.community.entity.groupBuyingBoard.GroupBuyingBoard, com.ssafy.TogetherBuyBilling.community.entity.groupBuyingBoard.QGroupBuyingBoard> groupBuyingBoards = this.<com.ssafy.TogetherBuyBilling.community.entity.groupBuyingBoard.GroupBuyingBoard, com.ssafy.TogetherBuyBilling.community.entity.groupBuyingBoard.QGroupBuyingBoard>createList("groupBuyingBoards", com.ssafy.TogetherBuyBilling.community.entity.groupBuyingBoard.GroupBuyingBoard.class, com.ssafy.TogetherBuyBilling.community.entity.groupBuyingBoard.QGroupBuyingBoard.class, PathInits.DIRECT2);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final ListPath<MemberLocation, QMemberLocation> memberLocations = this.<MemberLocation, QMemberLocation>createList("memberLocations", MemberLocation.class, QMemberLocation.class, PathInits.DIRECT2);

    public final StringPath nickname = createString("nickname");

    public final QPoint point;

    public final QMemberImage profileImage;

    public final QSeller seller;

    public final StringPath tel = createString("tel");

    public final QToken token;

    public final ListPath<com.ssafy.TogetherBuyBilling.shop.entity.product.Product, com.ssafy.TogetherBuyBilling.shop.entity.product.QProduct> wishedProducts = this.<com.ssafy.TogetherBuyBilling.shop.entity.product.Product, com.ssafy.TogetherBuyBilling.shop.entity.product.QProduct>createList("wishedProducts", com.ssafy.TogetherBuyBilling.shop.entity.product.Product.class, com.ssafy.TogetherBuyBilling.shop.entity.product.QProduct.class, PathInits.DIRECT2);

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.point = inits.isInitialized("point") ? new QPoint(forProperty("point"), inits.get("point")) : null;
        this.profileImage = inits.isInitialized("profileImage") ? new QMemberImage(forProperty("profileImage"), inits.get("profileImage")) : null;
        this.seller = inits.isInitialized("seller") ? new QSeller(forProperty("seller"), inits.get("seller")) : null;
        this.token = inits.isInitialized("token") ? new QToken(forProperty("token"), inits.get("token")) : null;
    }

}

