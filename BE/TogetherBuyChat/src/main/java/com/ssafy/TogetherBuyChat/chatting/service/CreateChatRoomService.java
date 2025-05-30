package com.ssafy.TogetherBuyChat.chatting.service;

import com.ssafy.TogetherBuyChat.chatting.dto.response.ResponseCreateChatRoomDTO;
import com.ssafy.TogetherBuyChat.chatting.entity.ChatRoom;
import com.ssafy.TogetherBuyChat.chatting.repository.jpa.ChatRoomRepository;
import com.ssafy.TogetherBuyChat.community.entity.groupBuyingBoard.GroupBuyingBoard;
import com.ssafy.TogetherBuyChat.community.repository.GroupBuyingBoardRepository;
import com.ssafy.TogetherBuyChat.member.entity.Member;
import com.ssafy.TogetherBuyChat.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final GroupBuyingBoardRepository groupBuyingBoardRepository;

    @Transactional
    public ResponseCreateChatRoomDTO createChatRoom(Long memberId, Long groupBuyingBoardId) {   // 채팅방 생성
        log.info("userIdDto: "+ memberId);

        // 1. 사용자 정보 가져오기
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        log.info("member: "+ member);
        log.info("memberId: "+ member.getMemberId());

        GroupBuyingBoard groupBuyingBoard = groupBuyingBoardRepository.findByGroupBuyingBoardId(groupBuyingBoardId);

        // 2. 채팅방 생성
        ChatRoom chatRoom = ChatRoom.builder()
                .creator(member)  // 채팅방 생성자 설정
                .participants(new HashSet<>())  // 참가자 리스트 초기화
                .groupBuyingBoard(groupBuyingBoard)
                .build();
        log.info("ChatRoom객체 생성 끝");
        chatRoomRepository.save(chatRoom);
        groupBuyingBoard.assignChatRoom(chatRoom);

        // 3.채팅방 개설자(사용자)를 채팅방에 자동 추가
        chatRoom.getParticipants().add(member);
        log.info("채팅방 참여자에 사용자 추가");

        // 4. 채팅방 저장
        chatRoom = chatRoomRepository.save(chatRoom);

        log.info("chatRoom.getGroupBuyingBoard 에 대한 내용!!!" + chatRoom.getGroupBuyingBoard());
        ResponseCreateChatRoomDTO responseCreateChatRoomDTO = new ResponseCreateChatRoomDTO();
        responseCreateChatRoomDTO.setChatRoomId(chatRoom.getChatRoomId());

        log.info("채팅방 생성 서비스 완료, chatRoomId : " + responseCreateChatRoomDTO.getChatRoomId());
        return responseCreateChatRoomDTO;  // 생성된 채팅방 반환
    }
}