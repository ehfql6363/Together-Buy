package com.ssafy.TogetherBuyMain.community.service;

import com.ssafy.TogetherBuyMain.community.dto.freeboard.*;
import com.ssafy.TogetherBuyMain.community.dto.page.PaginatedResponseDTO;
import com.ssafy.TogetherBuyMain.community.entity.freeBoard.FreeBoard;
import com.ssafy.TogetherBuyMain.community.entity.freeBoard.FreeBoardImage;
import com.ssafy.TogetherBuyMain.community.repository.freeBoard.FreeBoardRepository;
import com.ssafy.TogetherBuyMain.community.repository.freeBoard.like.FreeBoardLikeRepository;
import com.ssafy.TogetherBuyMain.global.AWS.S3Util;
import com.ssafy.TogetherBuyMain.global.util.FileUtil;
import com.ssafy.TogetherBuyMain.global.util.MemberUtil;
import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.member.repository.MemberRepository;
import com.ssafy.TogetherBuyMain.security.jwt.util.JwtUtil;
import com.ssafy.TogetherBuyMain.shop.dto.BooleanResponseDTO;
import com.ssafy.TogetherBuyMain.shop.entity.product.Product;
import com.ssafy.TogetherBuyMain.shop.repository.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FreeBoardService {

    private final int SIZE = 10;
    private final Sort SORT = Sort.by(Sort.Direction.DESC, "updatedAt");

    private final FreeBoardRepository freeBoardRepository;
    private final FreeBoardLikeRepository freeBoardLikeRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    private final S3Util s3Util;
    private final JwtUtil jwtUtil;
    private final MemberUtil memberUtil;

    @Transactional
    public PaginatedResponseDTO<FreeBoardResponseDTO> getFreeBoardList(int page) {
        Page<FreeBoard> boardPage = getFreeBoardPage(page);
        List<FreeBoardResponseDTO> boardList = getFreeBoardResponseDTOList(boardPage);

        return new PaginatedResponseDTO<>(
                boardList,
                boardPage.getNumber(),
                boardPage.getTotalPages(),
                boardPage.getTotalElements(),
                boardPage.getSize()
        );

    }

    @Transactional
    public FreeBoardDetailResponseDTO getFreeBoard(int no) {
        FreeBoard freeBoard = freeBoardRepository.getReferenceById((long) no);
        return FreeBoardDetailResponseDTO.of(freeBoard);
    }

    @Transactional
    public Long registerFreeBoard(
            String authorizationHeader,
            FreeBoardRegistrationRequestDTO requestDTO,
            List<MultipartFile> images) {
        String accessToken = jwtUtil.extractToken(authorizationHeader);
        Member member = memberUtil.getMember(accessToken);
        Product product = null;
        if (requestDTO.productId() != null) {
            product = productRepository.getReferenceById(requestDTO.productId());
        }

        FreeBoard newFreeBoard = registerFreeBoard(member, requestDTO, product);

        if(images == null || images.isEmpty()) return newFreeBoard.getId();

        List<FreeBoardImage> imageList = updateImageList(images, newFreeBoard, member);
        newFreeBoard.getImages().addAll(imageList);
        return newFreeBoard.getId();
    }

    @Transactional
    public FreeBoardUpdateResponseDTO updateFreeBoard(
            String authorizationHeader,
            Long boardId,
            FreeBoardUpdateRequestDTO requestDTO,
            List<MultipartFile> newImages) {
        String accessToken = jwtUtil.extractToken(authorizationHeader);
        Member member = memberUtil.getMember(accessToken);
        FreeBoard freeBoard = freeBoardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("FreeBoard not found"));

        if(isDifferentMember(member, freeBoard.getMember())){
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        // 새 이미지 등록 및 삭제
        updateImageList(newImages, freeBoard, member);

        // 자유 게시글 업데이트
        freeBoard.updateTitleAndContent(requestDTO.freeBoardTitle(), requestDTO.freeBoardContent());

        return FreeBoardUpdateResponseDTO.of(freeBoard);
    }

    @Transactional
    public BooleanResponseDTO deleteFreeBoard(
            String authorizationHeader,
            Long boardId) {
        String accessToken = jwtUtil.extractToken(authorizationHeader);
        Member member = memberUtil.getMember(accessToken);
        FreeBoard freeBoard = freeBoardRepository.getReferenceById(boardId);

        if(isDifferentMember(member, freeBoard.getMember())) {
            throw new IllegalArgumentException("작성자 아님");
        }

        deleteImage(freeBoard.getImages());
        freeBoardRepository.delete(freeBoard);
        return new BooleanResponseDTO(true);
    }

    @Transactional
    public BoardLikeResponseDTO likeFreeBoard(
            String authorizationHeader,
            Long boardId) {
        String accessToken = jwtUtil.extractToken(authorizationHeader);
        Member member = memberUtil.getMember(accessToken);
        FreeBoard freeBoard = freeBoardRepository.getReferenceById(boardId);

        boolean isLiked = freeBoardLikeRepository.isLiked(member, freeBoard);
        System.out.println(isLiked);
        toggleLikeStatus(member, freeBoard, isLiked);

        return new BoardLikeResponseDTO(freeBoard.getLikes());
    }

    private Page<FreeBoard> getFreeBoardPage(int page) {
        PageRequest pageable = PageRequest.of(page, SIZE, SORT);
        return freeBoardRepository.findAll(pageable);
    }

    private List<FreeBoardResponseDTO> getFreeBoardResponseDTOList(Page<FreeBoard> boardPage) {
        return boardPage.getContent().stream()
                .map(FreeBoardResponseDTO::of)
                .toList();
    }

    private FreeBoard registerFreeBoard(Member member, FreeBoardRegistrationRequestDTO requestDTO, Product product) {
        FreeBoard newFreeBoard = new FreeBoard(
                null,
                requestDTO.freeBoardTitle(),
                requestDTO.freeBoardContent(),
                0L, 0L,
                LocalDateTime.now(),
                LocalDateTime.now(),
                member,
                new ArrayList<>(),
                new ArrayList<>(),
                product,
                new ArrayList<>()
        );

        return freeBoardRepository.save(newFreeBoard);
    }

    private List<FreeBoardImage> updateImageList(List<MultipartFile> images, FreeBoard freeBoard, Member member) {
        List<FreeBoardImage> imageList = images.stream()
                .map(image -> registerImage(image, freeBoard, member))
                .toList();

        deleteImage(freeBoard.getImages());

        return freeBoard.getImages().addAll(imageList) ? imageList : new ArrayList<>();
    }

    private FreeBoardImage registerImage(MultipartFile image, FreeBoard freeBoard, Member member) {
        String url = s3Util.uploadFile(image, member.getMemberId());
        return new FreeBoardImage(null, url, FileUtil.createFileName(image.getOriginalFilename()), image.getContentType(), freeBoard);
    }

    private void deleteImage(List<FreeBoardImage> existingImages) {
        for (FreeBoardImage image : existingImages) {
            s3Util.deleteFile(image.getFileName());
        }
        existingImages.clear(); // 연관 관계에서 삭제
    }

    private boolean isDifferentMember(Member member, Member member2) {
        return !Objects.equals(member.getMemberId(), member2.getMemberId());
    }

    private void toggleLikeStatus(Member member, FreeBoard freeBoard, boolean isLike) {
        if(!isLike) {
            freeBoardLikeRepository.like(member, freeBoard);
            System.out.println("like!");
        } else {
            freeBoardLikeRepository.unlike(member, freeBoard);
            System.out.println("unlike!");
        }
    }
}
