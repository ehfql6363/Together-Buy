package com.ssafy.TogetherBuyChat.community.entity.groupBuyingBoard;

import com.ssafy.TogetherBuyChat.chatting.entity.ChatRoom;
import com.ssafy.TogetherBuyChat.member.entity.Member;
import com.ssafy.TogetherBuyChat.shop.entity.common.Form;
import com.ssafy.TogetherBuyChat.shop.entity.product.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class GroupBuyingBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupBuyingBoardId;

    private String title;
    private String content;
    private Long likes;
    private Long views;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private Member creator;

    // 신청서 (1:1)
    @OneToOne(mappedBy = "groupBuyingBoard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Form form;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "groupBuyingBoard", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupBuyingBoardImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "groupBuyingBoard", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupBuyingBoardLike> groupBuyingBoardLikes = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    public void updateTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateLikes() {
        this.likes = (long) this.groupBuyingBoardLikes.size();
    }

    public void increaseViews() {
        this.views += 1;
    }

    public void assignChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}