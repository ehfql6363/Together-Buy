package com.ssafy.TogetherBuyNotification.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import com.ssafy.TogetherBuyNotification.chatting.entity.ChatRoom;
import com.ssafy.TogetherBuyNotification.chatting.entity.QChatRoom;
import com.ssafy.TogetherBuyNotification.community.entity.comment.Comment;
import com.ssafy.TogetherBuyNotification.community.entity.comment.QComment;
import com.ssafy.TogetherBuyNotification.community.entity.freeBoard.FreeBoard;
import com.ssafy.TogetherBuyNotification.community.entity.freeBoard.FreeBoardLike;
import com.ssafy.TogetherBuyNotification.community.entity.freeBoard.QFreeBoard;
import com.ssafy.TogetherBuyNotification.community.entity.freeBoard.QFreeBoardLike;
import com.ssafy.TogetherBuyNotification.community.entity.groupBuyingBoard.GroupBuyingBoard;
import com.ssafy.TogetherBuyNotification.community.entity.groupBuyingBoard.GroupBuyingBoardLike;
import com.ssafy.TogetherBuyNotification.community.entity.groupBuyingBoard.QGroupBuyingBoard;
import com.ssafy.TogetherBuyNotification.community.entity.groupBuyingBoard.QGroupBuyingBoardLike;
import com.ssafy.TogetherBuyNotification.shop.entity.common.Apply;
import com.ssafy.TogetherBuyNotification.shop.entity.common.QApply;
import com.ssafy.TogetherBuyNotification.shop.entity.product.Product;
import com.ssafy.TogetherBuyNotification.shop.entity.product.QProduct;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1218317086L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final StringPath account = createString("account");

    public final ListPath<Apply, QApply> applies = this.<Apply, QApply>createList("applies", Apply.class, QApply.class, PathInits.DIRECT2);

    public final StringPath bank = createString("bank");

    public final DateTimePath<java.time.LocalDateTime> birth = createDateTime("birth", java.time.LocalDateTime.class);

    public final ListPath<ChatRoom, QChatRoom> chatRooms = this.<ChatRoom, QChatRoom>createList("chatRooms", ChatRoom.class, QChatRoom.class, PathInits.DIRECT2);

    public final ListPath<Comment, QComment> comments = this.<Comment, QComment>createList("comments", Comment.class, QComment.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final ListPath<FreeBoardLike, QFreeBoardLike> freeBoardLikes = this.<FreeBoardLike, QFreeBoardLike>createList("freeBoardLikes", FreeBoardLike.class, QFreeBoardLike.class, PathInits.DIRECT2);

    public final ListPath<FreeBoard, QFreeBoard> freeBoards = this.<FreeBoard, QFreeBoard>createList("freeBoards", FreeBoard.class, QFreeBoard.class, PathInits.DIRECT2);

    public final NumberPath<Long> gender = createNumber("gender", Long.class);

    public final ListPath<GroupBuyingBoardLike, QGroupBuyingBoardLike> groupBuyingBoardLikes = this.<GroupBuyingBoardLike, QGroupBuyingBoardLike>createList("groupBuyingBoardLikes", GroupBuyingBoardLike.class, QGroupBuyingBoardLike.class, PathInits.DIRECT2);

    public final ListPath<GroupBuyingBoard, QGroupBuyingBoard> groupBuyingBoards = this.<GroupBuyingBoard, QGroupBuyingBoard>createList("groupBuyingBoards", GroupBuyingBoard.class, QGroupBuyingBoard.class, PathInits.DIRECT2);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final ListPath<MemberLocation, QMemberLocation> memberLocations = this.<MemberLocation, QMemberLocation>createList("memberLocations", MemberLocation.class, QMemberLocation.class, PathInits.DIRECT2);

    public final StringPath nickname = createString("nickname");

    public final QPoint point;

    public final QMemberImage profileImage;

    public final QSeller seller;

    public final StringPath tel = createString("tel");

    public final QToken token;

    public final ListPath<Product, QProduct> wishedProducts = this.<Product, QProduct>createList("wishedProducts", Product.class, QProduct.class, PathInits.DIRECT2);

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

