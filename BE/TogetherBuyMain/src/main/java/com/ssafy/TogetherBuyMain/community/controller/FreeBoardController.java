package com.ssafy.TogetherBuyMain.community.controller;

import com.ssafy.TogetherBuyMain.community.dto.freeboard.*;
import com.ssafy.TogetherBuyMain.community.dto.page.PaginatedResponseDTO;
import com.ssafy.TogetherBuyMain.community.service.FreeBoardService;
import com.ssafy.TogetherBuyMain.shop.dto.BooleanResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/board/free")
@RequiredArgsConstructor
public class FreeBoardController {

    private final FreeBoardService freeBoardService;

    // 자유 게시글 목록 조회
    @GetMapping("")
    public ResponseEntity<PaginatedResponseDTO<FreeBoardResponseDTO>> getFreeBoard(
            @RequestParam(defaultValue = "0", name = "page") int page) {
        PaginatedResponseDTO<FreeBoardResponseDTO> boardList = freeBoardService.getFreeBoardList(page);
        return ResponseEntity.ok(boardList);
    }

    // 자유 게시글 상세 조회
    @GetMapping("{boardId}")
    public ResponseEntity<FreeBoardDetailResponseDTO> getFreeBoardList(@PathVariable(name = "boardId") int boardId) {
        FreeBoardDetailResponseDTO freeBoard = freeBoardService.getFreeBoard(boardId);
        return ResponseEntity.ok(freeBoard);
    }

    // 자유 게시글 등록
    @PostMapping("")
    public ResponseEntity<Long> registerFreeBoard(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestPart("request") FreeBoardRegistrationRequestDTO request,
            @RequestPart(name = "files", required = false) List<MultipartFile> freeBoardImages) {
        Long boardId = freeBoardService.registerFreeBoard(authorizationHeader, request, freeBoardImages);

        return ResponseEntity.ok(boardId);
    }

    // 자유 게시판 게시글 수정
    @PatchMapping("/{boardId}")
    public ResponseEntity<FreeBoardUpdateResponseDTO> updateFreeBoard(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("boardId") Long boardId,
            @RequestPart("request") FreeBoardUpdateRequestDTO request,
            @RequestPart(name = "files", required = false) List<MultipartFile> freeBoardImages) {
        FreeBoardUpdateResponseDTO freeBoard = freeBoardService.updateFreeBoard(authorizationHeader, boardId, request, freeBoardImages);
        return ResponseEntity.ok(freeBoard);
    }

    // 자유 게시글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<BooleanResponseDTO> deleteFreeBoard(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("boardId") Long boardId) {
        BooleanResponseDTO response = freeBoardService.deleteFreeBoard(authorizationHeader, boardId);
        return ResponseEntity.ok(response);
    }

    //
    @PostMapping("/{boardId}/like")
    public ResponseEntity<BoardLikeResponseDTO> likeFreeBoard(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("boardId") Long boardId) {
        BoardLikeResponseDTO response = freeBoardService.likeFreeBoard(authorizationHeader, boardId);
        return ResponseEntity.ok(response);
    }
}
