package com.ssafy.TogetherBuyChat.chatting.controller;

import com.ssafy.TogetherBuyChat.chatting.service.ChatImageService;
import com.ssafy.TogetherBuyChat.security.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/chat")
public class FileUploadController {

    private final ChatImageService chatImageService;
    private final JwtUtil jwtUtil;

    @PostMapping("/{chatRoomId}/image/upload")
    public ResponseEntity<?> uploadImage(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("chatRoomId") Long chatRoomId,
            @RequestPart("file") MultipartFile file) {
        
        log.info("일단 업로드 실행됨");

        // 이미지 업로드 처리
        String token = authorization.substring(7);  // 토큰에서 "Bearer " 제거
        log.info("token: " + token);

        Long memberId = jwtUtil.getMemberIdFromToken(token);
        log.info("이미지 memberId: " + memberId);
        
        String imageUrl = chatImageService.uploadImage(memberId ,chatRoomId, file);  // 임시로 memberId 7로 설정
        log.info("이미지 업로드 완료: " + imageUrl);

        return ResponseEntity.ok(
                Map.of("message", "이미지 송신 성공")
        );  // 이미지 URL 반환
    }
}
