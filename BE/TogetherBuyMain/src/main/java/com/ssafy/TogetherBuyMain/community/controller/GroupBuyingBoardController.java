package com.ssafy.TogetherBuyMain.community.controller;

import com.ssafy.TogetherBuyMain.community.dto.freeboard.BoardLikeResponseDTO;
import com.ssafy.TogetherBuyMain.community.dto.groupBuyingBoard.*;
import com.ssafy.TogetherBuyMain.community.dto.page.PaginatedResponseDTO;
import com.ssafy.TogetherBuyMain.community.service.GroupBuyingBoardService;
import com.ssafy.TogetherBuyMain.shop.dto.BooleanResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/board/group-buying")
@RequiredArgsConstructor
public class GroupBuyingBoardController {

    private final GroupBuyingBoardService groupBuyingBoardService;

    // 공동구매 게시글 등록
    @PostMapping("")
    public ResponseEntity<GroupBuyingBoardRegisterResponseDTO> registerGroupBuyingBoard(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestPart("request") GroupBuyingBoardRegisterRequestDTO request,
            @RequestPart(name = "files", required = false) List<MultipartFile> images) {

        GroupBuyingBoardRegisterResponseDTO results = groupBuyingBoardService.registerGroupBuyingBoard(
                authorizationHeader,
                request,
                images);

        return ResponseEntity.ok(results);
    }

    // 공구 게시판 목록 조회
    @GetMapping("/category/{categoryId}/sub-category/{subCategoryId}")
    public ResponseEntity<PaginatedResponseDTO<GroupBuyingBoardResponseDTO>> getGroupBuyingBoards(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @PathVariable("categoryId") Long categoryId,
            @PathVariable("subCategoryId") Long subCategoryId) {
        PaginatedResponseDTO<GroupBuyingBoardResponseDTO> boardList = groupBuyingBoardService.getGroupBuyingBoards(page, categoryId, subCategoryId);
        return ResponseEntity.ok(boardList);
    }

    // 공구 게시글 상세 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<GroupBuyingBoardDetailDTO> getBoardDetail(@PathVariable("boardId") Long boardId) {
        GroupBuyingBoardDetailDTO board = groupBuyingBoardService.getGroupBuyingBoard(boardId);
        return ResponseEntity.ok(board);
    }

    // 공구 게시글 작성자 조회
    @GetMapping("/{boardId}/writer")
    public ResponseEntity<GroupBuyingBoardWriterDTO> getBoardWriter(@PathVariable("boardId") Long boardId) {
        GroupBuyingBoardWriterDTO board = groupBuyingBoardService.getGroupBuyingBoardWriter(boardId);
        return ResponseEntity.ok(board);
    }

    // 공구 게시글 상품 조회
    @GetMapping("/{boardId}/product")
    public ResponseEntity<GroupBuyingBoardProductDTO> getBoardProduct(@PathVariable("boardId") Long boardId) {
        GroupBuyingBoardProductDTO board = groupBuyingBoardService.getGroupBuyingBoardProduct(boardId);
        return ResponseEntity.ok(board);
    }

    // 공구 게시글 수정
    @PatchMapping("/{boardId}")
    public ResponseEntity<GroupBuyingBoardUpdateResponseDTO> updateFreeBoard(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("boardId") Long boardId,
            @RequestPart("request") GroupBuyingBoardUpdateRequestDTO requestDTO,
            @RequestPart(name = "files", required = false) List<MultipartFile> images) {
        GroupBuyingBoardUpdateResponseDTO groupBuyingBoard = groupBuyingBoardService.updateGroupBuyingBoard(authorizationHeader, boardId, requestDTO, images);
        return ResponseEntity.ok(groupBuyingBoard);
    }

    // 공구 게시글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<BooleanResponseDTO> deleteFreeBoard(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("boardId") Long boardId) {
        BooleanResponseDTO response = groupBuyingBoardService.deleteGroupBuyingBoard(authorizationHeader, boardId);
        return ResponseEntity.ok(response);
    }

    // 공구 게시글 좋아요
    @PostMapping("/{boardId}/like")
    public ResponseEntity<BoardLikeResponseDTO> likeFreeBoard(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("boardId") Long boardId) {
        BoardLikeResponseDTO response = groupBuyingBoardService.likeFreeBoard(authorizationHeader, boardId);
        return ResponseEntity.ok(response);
    }
}
