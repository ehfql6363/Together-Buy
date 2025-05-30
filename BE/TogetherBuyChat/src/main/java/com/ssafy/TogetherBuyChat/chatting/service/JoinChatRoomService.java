package com.ssafy.TogetherBuyChat.chatting.service;

import com.ssafy.TogetherBuyChat.chatting.dto.DB.ChatMessageDTO;
import com.ssafy.TogetherBuyChat.chatting.dto.ChatRoomDataDTO;
import com.ssafy.TogetherBuyChat.chatting.dto.MemberInfoDTO;
import com.ssafy.TogetherBuyChat.chatting.dto.request.RequestMemberIdDTO;
import com.ssafy.TogetherBuyChat.chatting.dto.response.ResponseNoticeBroadcastDTO;
import com.ssafy.TogetherBuyChat.chatting.entity.ChatRoom;
import com.ssafy.TogetherBuyChat.chatting.repository.jpa.ChatRoomRepository;
import com.ssafy.TogetherBuyChat.member.entity.Member;
import com.ssafy.TogetherBuyChat.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class JoinChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ChatMessageStorageService chatMessageStorageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Transactional
    public ChatRoomDataDTO joinChatRoom(Long memberId, String nickname, Long chatRoomId) {

        // 특정 채팅방에 대한 memberId들의 set
        Set<Long> participants = chatRoomRepository.findMemberIdsByChatRoomId(chatRoomId);

        log.info("채팅방 인원들 목록 : " + participants);
        log.info("멤버 아이디 : " + memberId);

        if (!participants.contains(memberId)) {
            log.info("신규입장!!!");
            // 신규입장에 대한 코드
            addMemberToChatRoom(chatRoomId, memberId);
            log.info("채팅방 신규 입장 완료");

            String msg = nickname + "님이 들어왔습니다.";

            log.info(msg);

            log.info("브로드캐스트!!!");
            //채팅방 인원들에게 들어왔다고 브로드캐스트
            simpMessagingTemplate.convertAndSend("/sub/chat/room/" + chatRoomId, new ResponseNoticeBroadcastDTO(msg));
            log.info("닉네임: "+ nickname);
        }else {
            log.info("재입장!!!");

            String msg = nickname + "님이 다시 입장했습니다.";
            
//            재입장 했을 때 브로드캐스트 확인용
//            simpMessagingTemplate.convertAndSend("/sub/chat/room/" + chatRoomId, new ResponseNoticeBroadcastDTO(msg));

            log.info("재입장" + msg);
            // 재입장에 대한 코드
        }

        // 채팅방 메시지 내역을 모아놓은 List
        List<ChatMessageDTO> chatMessages = chatMessageStorageService.getChatMessages(chatRoomId);
        log.info("메시지 조회" + chatMessages);

        // 채팅방 멤버 조회        memberId, nickname,
        List<MemberInfoDTO> memberInfos = chatMessageStorageService.getMemberInfos(chatRoomId);

        log.info("유저 정보 조회!!!!!!!!!!!!");
        for (MemberInfoDTO e : memberInfos) {
            log.info(e.getMemberId() + " | " + e.getNickname() +" | "+ e.getImageUrl());
        }

        ChatRoomDataDTO chatRoomDataDTO = ChatRoomDataDTO.builder()
                .chatMessages(chatMessages)
                .memberInfos(memberInfos)
                .build();
        log.info("chatRoomDataDTO: " + chatRoomDataDTO);

        return chatRoomDataDTO;  // 업데이트된 채팅방 반환
    }

    /**
     * 특정 채팅방에 사용자를 추가하는 메서드
     * @param chatRoomId 채팅방 ID
     * @param memberId 사용자 ID
     * @return 업데이트된 ChatRoom 객체
     */
    private void addMemberToChatRoom(Long chatRoomId, Long memberId) {
        // 1. 채팅방 가져오기
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));

        // 2. 사용자 정보 가져오기
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 3. 채팅방에 사용자 추가
        chatRoom.getParticipants().add(member);

        // 4. MySql에 사용자를 추가한 채팅방 저장
        chatRoomRepository.save(chatRoom);
    }


}