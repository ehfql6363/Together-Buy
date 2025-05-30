package com.ssafy.TogetherBuyMain.community.controller;

import com.ssafy.TogetherBuyMain.community.dto.comment.CommentRegisterResponseDTO;
import com.ssafy.TogetherBuyMain.community.dto.comment.CommentResponseDTO;
import com.ssafy.TogetherBuyMain.community.dto.page.PaginatedResponseDTO;
import com.ssafy.TogetherBuyMain.community.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 자유 게시글 댓글 작성
    @PostMapping("/board/{boardId}")
    public ResponseEntity<CommentRegisterResponseDTO> registerComment(
            @PathVariable("boardId") Long boardId,
            @RequestBody String content,
            @RequestHeader("Authorization") String authorizationHeader) {
        CommentRegisterResponseDTO results = commentService.registerComment(boardId, content, authorizationHeader);
        return ResponseEntity.ok(results);
    }

    // 자유 게시글 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("commentId") Long commentId,
            @RequestHeader("Authorization") String authorizationHeader) {
        Boolean result = commentService.deleteComment(commentId, authorizationHeader);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/free/{boardId}")
    public ResponseEntity<PaginatedResponseDTO<CommentResponseDTO>> getCommentList(
            @PathVariable("boardId") Long boardId,
            @RequestParam(defaultValue = "0", name = "page") int page) {
        PaginatedResponseDTO<CommentResponseDTO> results = commentService.getCommentList(boardId, page);
        return ResponseEntity.ok(results);
    }
}
