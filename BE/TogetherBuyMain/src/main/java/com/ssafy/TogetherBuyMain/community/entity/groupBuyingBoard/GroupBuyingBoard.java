package com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard;

import com.ssafy.TogetherBuyMain.chatting.entity.ChatRoom;
import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.shop.entity.common.Form;
import com.ssafy.TogetherBuyMain.shop.entity.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @OneToOne(mappedBy = "groupBuyingBoard", cascade = CascadeType.ALL)
    @JoinColumn(name = "form_id")
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

}