package com.ssafy.TogetherBuyMain.community.service;

import com.ssafy.TogetherBuyMain.community.dto.comment.CommentRegisterResponseDTO;
import com.ssafy.TogetherBuyMain.community.dto.comment.CommentResponseDTO;
import com.ssafy.TogetherBuyMain.community.dto.page.PaginatedResponseDTO;
import com.ssafy.TogetherBuyMain.community.entity.comment.Comment;
import com.ssafy.TogetherBuyMain.community.entity.freeBoard.FreeBoard;
import com.ssafy.TogetherBuyMain.community.repository.comment.CommentRepository;
import com.ssafy.TogetherBuyMain.community.repository.freeBoard.FreeBoardRepository;
import com.ssafy.TogetherBuyMain.global.util.MemberUtil;
import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.member.repository.MemberRepository;
import com.ssafy.TogetherBuyMain.security.jwt.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final int SIZE = 10;
    private final Sort SORT = Sort.by(Sort.Direction.DESC, "createdAt");

    private final JwtUtil jwtUtil;
    private final MemberUtil memberUtil;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final FreeBoardRepository freeBoardRepository;

    @Transactional
    public CommentRegisterResponseDTO registerComment(Long boardId, String content, String authorizationHeader) {
        String accessToken = jwtUtil.extractToken(authorizationHeader);
        Member member = memberUtil.getMember(accessToken);
        FreeBoard freeBoard = freeBoardRepository.getReferenceById(boardId);
        Comment newComment = createNewComment(content, member, freeBoard);

        commentRepository.save(newComment);

        member.getComments().add(newComment);
        freeBoard.getComments().add(newComment);

        return CommentRegisterResponseDTO.of(newComment, member);
    }

    @Transactional
    public Boolean deleteComment(Long commentId, String authorizationHeader) {
        String accessToken = jwtUtil.extractToken(authorizationHeader);
        Member member = memberUtil.getMember(accessToken);
        Comment comment = commentRepository.getReferenceById(commentId);

        if(!comment.getMember().equals(member))
            throw new IllegalArgumentException("댓글 작성자가 아닙니다.");

        deleteCommentFromFreeBoard(comment);
        deleteCommentFromMember(comment, member);

        return true;
    }

    @Transactional
    public PaginatedResponseDTO<CommentResponseDTO> getCommentList(Long boardId, int page) {
        Page<Comment> commentPage = getCommentPage(boardId, page);
        List<CommentResponseDTO> commentList = getCommentResponseDTOList(commentPage);

        return new PaginatedResponseDTO<>(
                commentList,
                commentPage.getNumber(),
                commentPage.getTotalPages(),
                commentPage.getTotalElements(),
                commentPage.getSize()
        );
    }

    private Comment createNewComment(String content, Member member, FreeBoard freeBoard) {
        return new Comment(null, content, LocalDateTime.now(), member, freeBoard);
    }

    private void deleteCommentFromFreeBoard(Comment comment) {
        FreeBoard freeBoard = comment.getFreeBoard();
        freeBoard.getComments().remove(comment);
    }

    private void deleteCommentFromMember(Comment comment, Member member) {
        member.getComments().remove(comment);
    }

    private Page<Comment> getCommentPage(Long boardId, int page) {
        PageRequest pageable = PageRequest.of(page, SIZE, SORT);
        return commentRepository.findCommentsByBoardId(boardId, pageable);
    }

    private List<CommentResponseDTO> getCommentResponseDTOList(Page<Comment> commentPage) {
        return commentPage.getContent().stream()
                .map(CommentResponseDTO::of)
                .toList();
    }
}
