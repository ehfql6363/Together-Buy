package com.ssafy.TogetherBuyChat.chatting.repository.mongo;

import com.ssafy.TogetherBuyChat.chatting.dto.DB.ChatMessageDTO;
import com.ssafy.TogetherBuyChat.chatting.entity.MongoChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<MongoChatMessage, String> {
    // 기본적인 CRUD 메서드는 MongoRepository에서 제공

    List<MongoChatMessage> findByChatRoomId(Long chatRoomId); // 채팅방Id에 해당하는 모든 데이터 가져옴

}
