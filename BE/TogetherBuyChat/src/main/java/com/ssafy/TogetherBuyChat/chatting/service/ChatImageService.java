package com.ssafy.TogetherBuyChat.chatting.service;

import com.ssafy.TogetherBuyChat.chatting.dto.DB.ChatMessageDTO;
import com.ssafy.TogetherBuyChat.chatting.dto.response.ResponseImageChatMessageDTO;
import com.ssafy.TogetherBuyChat.global.AWS.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatImageService {

    private final S3Util s3Util;
    private final ChatMessageStorageService chatMessageStorageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public String uploadImage(Long memberId, Long chatRoomId, MultipartFile file) {
        // 이미지 업로드 처리
        String chatImg = s3Util.uploadFile(file, chatRoomId);  // S3에 이미지 업로드
        log.info("S3에 이미지 업로드 완료");

        // 프론트에게 뿌려줄 메시지 DTO
        ResponseImageChatMessageDTO responseImageChatMessageDTO = ResponseImageChatMessageDTO.builder()
                .chatRoomId(chatRoomId)
                .senderId(memberId)
                .chatImg(chatImg)
                .timestamp(LocalDateTime.now())
                .build();

        // 몽고DB에 저장하기 위한 메시지 DTO
        ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder()
                .chatRoomId(chatRoomId)
                .senderId(memberId)   // 임시로 memberId를 7로 설정
                .content("")
                .chatImg(chatImg)
                .timestamp(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();

        // 2. 메시지 객체를 몽고 디비에 저장하기
        chatMessageStorageService.saveMessage(chatMessageDTO);
        log.info("디비에 저장 완료");

        // 3. 메시지 전송
        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + chatRoomId, responseImageChatMessageDTO);
        log.info("이미지 담은 메시지 브로드캐스트 완료");

        return chatImg;  // 이미지 URL 반환
    }
}
