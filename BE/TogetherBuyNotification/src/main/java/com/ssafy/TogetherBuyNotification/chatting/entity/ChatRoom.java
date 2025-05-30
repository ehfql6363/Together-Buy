package com.ssafy.TogetherBuyNotification.chatting.entity;

import com.ssafy.TogetherBuyNotification.community.entity.groupBuyingBoard.GroupBuyingBoard;
import com.ssafy.TogetherBuyNotification.member.entity.Member;
import com.ssafy.TogetherBuyNotification.shop.entity.order.Order;
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
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "chat_rooms")
@NoArgsConstructor
@AllArgsConstructor
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
