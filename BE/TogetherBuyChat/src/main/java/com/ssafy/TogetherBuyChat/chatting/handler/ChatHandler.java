package com.ssafy.TogetherBuyChat.chatting.handler;

import com.ssafy.TogetherBuyChat.chatting.dto.DB.ChatMessageDTO;
import com.ssafy.TogetherBuyChat.chatting.dto.request.RequestChatMessageDTO;
import com.ssafy.TogetherBuyChat.chatting.dto.response.ResponseTextChatMessageDTO;
import com.ssafy.TogetherBuyChat.chatting.service.ChatMessageStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatHandler {

    private final ChatMessageStorageService chatMessageStorageService;

    // 클라이언트가 "/sub/chat/room/{roomId}"으로 보내는 메시지 처리
    @MessageMapping("/chat/room/{chatRoomId}")  // 프론트가 여기로 보낸 메세지 처리
    @SendTo("/sub/chat/room/{chatRoomId}")  // 해당 채팅방을 구독하는 클라이언트에게 메시지 전송
    public ResponseTextChatMessageDTO sendMessage(
            RequestChatMessageDTO requestChatMessageDTO) {
        log.info("sendMessage에 들어옴");
        log.info(requestChatMessageDTO.toString());

        // 프론트에 Response 할 메시지 DTO 생성
        ResponseTextChatMessageDTO responseTextChatMessageDTO = ResponseTextChatMessageDTO.builder()
                .chatRoomId(requestChatMessageDTO.chatRoomId())
                .senderId(requestChatMessageDTO.senderId())
                .content(requestChatMessageDTO.content())
                .timestamp(LocalDateTime.now().plusHours(9))
                .build();

        // 몽고DB에 저장할 메시지 DTO 생성
        ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder()
                .chatRoomId(requestChatMessageDTO.chatRoomId())
                .senderId(requestChatMessageDTO.senderId())
                .content(requestChatMessageDTO.content())
                .chatImg("")
                .timestamp(LocalDateTime.now().plusHours(9))
                .build();

        // 채팅을 보낼 때 마다 MongoDB에 저장
        chatMessageStorageService.saveMessage(chatMessageDTO);
        log.info("MongoDB저장 완료");

        return responseTextChatMessageDTO;  // 받은 메시지를 그대로 해당 채팅방에 전송
    }
    // 반환된 메시지데이터는 프론트가 전달 받음 프론트가 전달 받아서 텍스트면 텍스트를 이미지면 URL을 통해 S3에 접근해서 이미지를 띄워줌
}