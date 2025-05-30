package com.ssafy.TogetherBuyChat.chatting.service;

import com.ssafy.TogetherBuyChat.chatting.dto.DB.ChatMessageDTO;
import com.ssafy.TogetherBuyChat.chatting.dto.MemberInfoDTO;
import com.ssafy.TogetherBuyChat.chatting.entity.MongoChatMessage;
import com.ssafy.TogetherBuyChat.chatting.repository.jpa.ChatRoomRepository;
import com.ssafy.TogetherBuyChat.chatting.repository.mongo.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageStorageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    // 채팅 데이터 저장
    public void saveMessage(ChatMessageDTO chatMessageDTO) {
        MongoChatMessage entity = MongoChatMessage.builder()
                .chatRoomId(chatMessageDTO.getChatRoomId())
                .senderId(chatMessageDTO.getSenderId())
                .content(chatMessageDTO.getContent())
                .chatImg(chatMessageDTO.getChatImg())
                .timestamp(chatMessageDTO.getTimestamp())
                .build();
        chatMessageRepository.save(entity);
    }

    // 채팅방 메시지 내역 조회
    public List<ChatMessageDTO> getChatMessages(Long chatRoomId) {
        // MongoDB에서 채팅방에 해당하는 메시지를 DTO 형태로 조회
        // todo : ChatMessageDTO 변환
        List<MongoChatMessage> messages = chatMessageRepository.findByChatRoomId(chatRoomId);

        return messages.stream().map((message) -> ChatMessageDTO.builder()
                .chatRoomId(message.getChatRoomId())
                .senderId(message.getSenderId())
                .content(message.getContent())
                .chatImg(message.getChatImg())
                .timestamp(message.getTimestamp())
                .build()).toList();
    }
    
    // 채팅방 유저들 정보 조회
    public List<MemberInfoDTO> getMemberInfos(Long chatRoomId) {
        // MongoDB에서 채팅방에 해당하는 멤버정보들을 DTO 형태로 조회
        return chatRoomRepository.findMemberInfosByChatRoomId(chatRoomId);
    }

}
