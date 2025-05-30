package com.ssafy.TogetherBuyNotification.member.entity;

import com.ssafy.TogetherBuyNotification.chatting.entity.ChatRoom;
import com.ssafy.TogetherBuyNotification.community.entity.comment.Comment;
import com.ssafy.TogetherBuyNotification.community.entity.freeBoard.FreeBoard;
import com.ssafy.TogetherBuyNotification.community.entity.freeBoard.FreeBoardLike;
import com.ssafy.TogetherBuyNotification.community.entity.groupBuyingBoard.GroupBuyingBoard;
import com.ssafy.TogetherBuyNotification.community.entity.groupBuyingBoard.GroupBuyingBoardLike;
import com.ssafy.TogetherBuyNotification.shop.entity.common.Apply;
import com.ssafy.TogetherBuyNotification.shop.entity.product.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String nickname;
    private LocalDateTime birth;
    private Long gender;
    private String tel;

    @Column(unique = true)
    private String email;
    private String bank;
    private String account;

    // 사용자 관련 필드
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberLocation> memberLocations = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "point_id")
    private Point point;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_image_id")
    private MemberImage profileImage;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FreeBoard> freeBoards = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FreeBoardLike> freeBoardLikes = new ArrayList<>();

    // 작성한 공동 구매 게시글 (1:N)
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupBuyingBoard> groupBuyingBoards = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupBuyingBoardLike> groupBuyingBoardLikes = new ArrayList<>();

    // 참여한 신청서 (1:N)
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apply> applies = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinTable(
            name = "product_wish", // 연결 테이블 이름
            joinColumns = @JoinColumn(name = "member_id"), // Member 엔티티의 외래 키 컬럼 이름
            inverseJoinColumns = @JoinColumn(name = "product_id") // Product 엔티티의 외래 키 컬럼 이름
    )
    private List<Product> wishedProducts = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoom> chatRooms = new ArrayList<>();

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Seller seller;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Token token;

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateBankInfo(String bank, String account) {
        this.bank = bank;
        this.account = account;
    }
}
