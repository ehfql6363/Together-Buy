package com.ssafy.TogetherBuyChat.chatting.controller;

import com.ssafy.TogetherBuyChat.chatting.dto.ChatRoomDataDTO;
import com.ssafy.TogetherBuyChat.chatting.dto.request.RequestGroupBuyingBoardIdDTO;
import com.ssafy.TogetherBuyChat.chatting.dto.response.ResponseChatRoomListDTO;
import com.ssafy.TogetherBuyChat.chatting.dto.response.ResponseCreateChatRoomDTO;
import com.ssafy.TogetherBuyChat.chatting.service.CreateChatRoomService;
import com.ssafy.TogetherBuyChat.chatting.service.JoinChatRoomService;
import com.ssafy.TogetherBuyChat.chatting.service.ListChatRoomsService;
import com.ssafy.TogetherBuyChat.security.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/chat/rooms")
public class ChatRoomController {

    private final CreateChatRoomService createChatRoomService;
    private final JoinChatRoomService joinChatRoomService;
    private final ListChatRoomsService listChatRoomsService;
    private final JwtUtil jwtUtil;

    // 채팅방 생성
    @PostMapping("/create")
    public ResponseEntity<?> createChatRoom(
            @RequestHeader(name = "Authorization",required = false) String authorization,   // JWT
            @RequestBody RequestGroupBuyingBoardIdDTO requestGroupBuyingBoardIdDTO
    ) {
        log.info("채팅방 개설");
        String token = authorization.substring(7);
        Long memberId = jwtUtil.getMemberIdFromToken(token);
        log.info("memberId: " + memberId);

        Long groupBuyingBoardId = requestGroupBuyingBoardIdDTO.groupBuyingBoardId();
        ResponseCreateChatRoomDTO responseCreateChatRoomDTO = createChatRoomService.createChatRoom(memberId, groupBuyingBoardId);
        log.info("chatRoomService 실행 완료");

        return ResponseEntity.ok(responseCreateChatRoomDTO);
    }

    // 채팅방 참가
    @GetMapping("/join/{chatRoomId}")
    public ResponseEntity<?> joinChatRoom(
            @RequestHeader(name = "Authorization",required = false) String authorization,
//            @RequestBody RequestMemberIdDTO requestUserIdDTO,
            @PathVariable Long chatRoomId) {

        String token = authorization.substring(7);
        Long memberId = jwtUtil.getMemberIdFromToken(token);
        String nickname = jwtUtil.getNicknameFromToken(token);

        log.info("컨트롤러단 chatRoomId: "+ chatRoomId);
        ChatRoomDataDTO chatRoomDataDTO = joinChatRoomService.joinChatRoom(memberId, nickname, chatRoomId);
        log.info("채팅방 참가 성공");

        return ResponseEntity.ok(chatRoomDataDTO);
    }


    // 채팅방 리스트 조회
    @GetMapping("/list")
    public ResponseEntity<?> listChatRooms(
            @RequestHeader(name = "Authorization",required = false) String authorization) {

        String token = authorization.substring(7);

        Long memberId = jwtUtil.getMemberIdFromToken(token);
        log.info("memberId: " + memberId);

        Set<Long> chatRoomList = listChatRoomsService.getChatRoomIdsByMemberId(memberId);
        ResponseChatRoomListDTO chatRoomListDTO = ResponseChatRoomListDTO.builder()
                .chatRoomIds(chatRoomList)
                .build();

        log.info("chatRoomList: " + chatRoomList);
        return ResponseEntity.ok(chatRoomListDTO);
    }

}
