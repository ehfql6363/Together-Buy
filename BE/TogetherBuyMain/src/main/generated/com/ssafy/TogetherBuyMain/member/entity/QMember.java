package com.ssafy.TogetherBuyMain.member.entity;

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

    private static final long serialVersionUID = 665090337L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final StringPath account = createString("account");

    public final ListPath<com.ssafy.TogetherBuyMain.shop.entity.common.Apply, com.ssafy.TogetherBuyMain.shop.entity.common.QApply> applies = this.<com.ssafy.TogetherBuyMain.shop.entity.common.Apply, com.ssafy.TogetherBuyMain.shop.entity.common.QApply>createList("applies", com.ssafy.TogetherBuyMain.shop.entity.common.Apply.class, com.ssafy.TogetherBuyMain.shop.entity.common.QApply.class, PathInits.DIRECT2);

    public final StringPath bank = createString("bank");

    public final DatePath<java.time.LocalDate> birth = createDate("birth", java.time.LocalDate.class);

    public final ListPath<com.ssafy.TogetherBuyMain.chatting.entity.ChatRoom, com.ssafy.TogetherBuyMain.chatting.entity.QChatRoom> chatRooms = this.<com.ssafy.TogetherBuyMain.chatting.entity.ChatRoom, com.ssafy.TogetherBuyMain.chatting.entity.QChatRoom>createList("chatRooms", com.ssafy.TogetherBuyMain.chatting.entity.ChatRoom.class, com.ssafy.TogetherBuyMain.chatting.entity.QChatRoom.class, PathInits.DIRECT2);

    public final ListPath<com.ssafy.TogetherBuyMain.community.entity.comment.Comment, com.ssafy.TogetherBuyMain.community.entity.comment.QComment> comments = this.<com.ssafy.TogetherBuyMain.community.entity.comment.Comment, com.ssafy.TogetherBuyMain.community.entity.comment.QComment>createList("comments", com.ssafy.TogetherBuyMain.community.entity.comment.Comment.class, com.ssafy.TogetherBuyMain.community.entity.comment.QComment.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final ListPath<com.ssafy.TogetherBuyMain.community.entity.freeBoard.FreeBoardLike, com.ssafy.TogetherBuyMain.community.entity.freeBoard.QFreeBoardLike> freeBoardLikes = this.<com.ssafy.TogetherBuyMain.community.entity.freeBoard.FreeBoardLike, com.ssafy.TogetherBuyMain.community.entity.freeBoard.QFreeBoardLike>createList("freeBoardLikes", com.ssafy.TogetherBuyMain.community.entity.freeBoard.FreeBoardLike.class, com.ssafy.TogetherBuyMain.community.entity.freeBoard.QFreeBoardLike.class, PathInits.DIRECT2);

    public final ListPath<com.ssafy.TogetherBuyMain.community.entity.freeBoard.FreeBoard, com.ssafy.TogetherBuyMain.community.entity.freeBoard.QFreeBoard> freeBoards = this.<com.ssafy.TogetherBuyMain.community.entity.freeBoard.FreeBoard, com.ssafy.TogetherBuyMain.community.entity.freeBoard.QFreeBoard>createList("freeBoards", com.ssafy.TogetherBuyMain.community.entity.freeBoard.FreeBoard.class, com.ssafy.TogetherBuyMain.community.entity.freeBoard.QFreeBoard.class, PathInits.DIRECT2);

    public final NumberPath<Integer> gender = createNumber("gender", Integer.class);

    public final ListPath<com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoardLike, com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.QGroupBuyingBoardLike> groupBuyingBoardLikes = this.<com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoardLike, com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.QGroupBuyingBoardLike>createList("groupBuyingBoardLikes", com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoardLike.class, com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.QGroupBuyingBoardLike.class, PathInits.DIRECT2);

    public final ListPath<com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoard, com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.QGroupBuyingBoard> groupBuyingBoards = this.<com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoard, com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.QGroupBuyingBoard>createList("groupBuyingBoards", com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoard.class, com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.QGroupBuyingBoard.class, PathInits.DIRECT2);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final ListPath<MemberLocation, QMemberLocation> memberLocations = this.<MemberLocation, QMemberLocation>createList("memberLocations", MemberLocation.class, QMemberLocation.class, PathInits.DIRECT2);

    public final QMemberPoint memberPoint;

    public final StringPath nickname = createString("nickname");

    public final QMemberImage profileImage;

    public final EnumPath<Provider> provider = createEnum("provider", Provider.class);

    public final StringPath providerId = createString("providerId");

    public final com.ssafy.TogetherBuyMain.security.jwt.entity.QRefreshToken RefreshToken;

    public final QSeller seller;

    public final StringPath tel = createString("tel");

    public final StringPath verificationCode = createString("verificationCode");

    public final DateTimePath<java.time.LocalDateTime> verificationCodeExpireTime = createDateTime("verificationCodeExpireTime", java.time.LocalDateTime.class);

    public final ListPath<com.ssafy.TogetherBuyMain.shop.entity.product.Product, com.ssafy.TogetherBuyMain.shop.entity.product.QProduct> wishedProducts = this.<com.ssafy.TogetherBuyMain.shop.entity.product.Product, com.ssafy.TogetherBuyMain.shop.entity.product.QProduct>createList("wishedProducts", com.ssafy.TogetherBuyMain.shop.entity.product.Product.class, com.ssafy.TogetherBuyMain.shop.entity.product.QProduct.class, PathInits.DIRECT2);

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
        this.memberPoint = inits.isInitialized("memberPoint") ? new QMemberPoint(forProperty("memberPoint"), inits.get("memberPoint")) : null;
        this.profileImage = inits.isInitialized("profileImage") ? new QMemberImage(forProperty("profileImage"), inits.get("profileImage")) : null;
        this.RefreshToken = inits.isInitialized("RefreshToken") ? new com.ssafy.TogetherBuyMain.security.jwt.entity.QRefreshToken(forProperty("RefreshToken"), inits.get("RefreshToken")) : null;
        this.seller = inits.isInitialized("seller") ? new QSeller(forProperty("seller"), inits.get("seller")) : null;
    }

}

