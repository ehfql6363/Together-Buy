package com.ssafy.TogetherBuyChat.chatting.service;

import com.ssafy.TogetherBuyChat.chatting.repository.jpa.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ListChatRoomsService {

    private final ChatRoomRepository chatRoomRepository;

    @Transactional(readOnly = true)
    public Set<Long> getChatRoomIdsByMemberId(Long memberId) {
        return chatRoomRepository.findChatRoomIdsByMemberId(memberId);
    }
}
