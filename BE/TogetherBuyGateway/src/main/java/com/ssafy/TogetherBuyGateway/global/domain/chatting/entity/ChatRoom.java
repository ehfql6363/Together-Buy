package com.ssafy.TogetherBuyGateway.global.domain.chatting.entity;

import com.ssafy.TogetherBuyGateway.global.domain.community.entity.groupBuyingBoard.GroupBuyingBoard;
import com.ssafy.TogetherBuyGateway.global.domain.shop.entity.order.Order;
import com.ssafy.TogetherBuyGateway.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_buying_board_id", unique = true)
    private GroupBuyingBoard groupBuyingBoard;

    // 채팅방 참여자들에 대한 다대다 관계
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "chatroom_participants",
            joinColumns = @JoinColumn(name = "chat_room_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private Set<Member> participants = new HashSet<>();

    // 공고글을 만든 유저 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Member creator;

}
