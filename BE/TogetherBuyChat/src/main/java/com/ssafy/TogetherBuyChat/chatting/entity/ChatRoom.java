package com.ssafy.TogetherBuyChat.chatting.entity;

import com.ssafy.TogetherBuyChat.community.entity.groupBuyingBoard.GroupBuyingBoard;
import com.ssafy.TogetherBuyChat.member.entity.Member;
import com.ssafy.TogetherBuyChat.shop.entity.order.Order;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor  //기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 포함하는 생성자 자동 생성
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동증가 방식으로 PK 자동 할당
    // IDENTITY 전략은 데이터베이스가 자동으로 ID 값을 생성하는 방식(MySQL의 AUTO_INCREMENT)을 따름
    private Long chatRoomId;    // 채팅방의 고유


//    FetchType.LAZY (지연 로딩)
//    필요할 때만 데이터를 가져옴.
//    처음에는 NULL 상태이고, 객체를 참조하는 순간 쿼리가 실행됩니다.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_buying_board_id", unique = true)
    private GroupBuyingBoard groupBuyingBoard;


    // 채팅방 참여자들에 대한 다대다 관계
    @ManyToMany(fetch = FetchType.LAZY) // 다대다 관계, 지연로딩
    @JoinTable(
            name = "chatroom_participants",
            joinColumns = @JoinColumn(name = "chat_room_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private Set<Member> participants = new HashSet<>();


    // 공고글을 만든 유저 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")// 채팅방을 만든 사람
    private Member creator;

}
