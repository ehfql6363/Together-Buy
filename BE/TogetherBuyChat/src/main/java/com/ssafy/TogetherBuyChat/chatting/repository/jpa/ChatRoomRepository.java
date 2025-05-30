package com.ssafy.TogetherBuyChat.chatting.repository.jpa;

import com.ssafy.TogetherBuyChat.chatting.dto.MemberInfoDTO;
import com.ssafy.TogetherBuyChat.chatting.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    // chatRoomId로 그안의 멤버Id들로 각 멤버들의 nickname과 프로필이미지url을 가져옴
    @Query("SELECT new com.ssafy.TogetherBuyChat.chatting.dto.MemberInfoDTO(m.memberId, m.nickname, i.url) " +
            "FROM ChatRoom c " +
            "JOIN c.participants m " +
            "LEFT JOIN MemberImage i ON m.memberId = i.member.memberId " +
            "WHERE c.chatRoomId = :chatRoomId")
    List<MemberInfoDTO> findMemberInfosByChatRoomId(@Param("chatRoomId") Long chatRoomId);

    
    // chatRoomId로 그안의 memberId들을 리스트로 뽑아옴
    @Query("SELECT m.memberId " +
            "FROM ChatRoom c " +
            "JOIN c.participants m " +
            "WHERE c.chatRoomId = :chatRoomId")
    Set<Long> findMemberIdsByChatRoomId(@Param("chatRoomId") Long chatRoomId);

    // 특정 유저가 속한 채팅방 ID 리스트 조회
    @Query("SELECT c.chatRoomId " +
            "FROM ChatRoom c " +
            "JOIN c.participants m " +
            "WHERE m.memberId = :memberId")
    Set<Long> findChatRoomIdsByMemberId(@Param("memberId") Long memberId);




}