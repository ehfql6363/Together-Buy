package com.ssafy.TogetherBuyMain.community.service;

import com.ssafy.TogetherBuyMain.community.dto.freeboard.BoardLikeResponseDTO;
import com.ssafy.TogetherBuyMain.community.dto.groupBuyingBoard.*;
import com.ssafy.TogetherBuyMain.community.dto.page.PaginatedResponseDTO;
import com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoard;
import com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoardImage;
import com.ssafy.TogetherBuyMain.community.repository.groupBuyingBoard.GroupBuyingBoardRepository;
import com.ssafy.TogetherBuyMain.community.repository.groupBuyingBoard.like.GroupBuyingBoardLikeRepository;
import com.ssafy.TogetherBuyMain.global.AWS.S3Util;
import com.ssafy.TogetherBuyMain.global.util.FileUtil;
import com.ssafy.TogetherBuyMain.global.util.MemberUtil;
import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.member.repository.MemberRepository;
import com.ssafy.TogetherBuyMain.security.jwt.util.JwtUtil;
import com.ssafy.TogetherBuyMain.shop.dto.BooleanResponseDTO;
import com.ssafy.TogetherBuyMain.shop.entity.product.Product;
import com.ssafy.TogetherBuyMain.shop.repository.category.CategoryRepository;
import com.ssafy.TogetherBuyMain.shop.repository.category.SubCategoryRepository;
import com.ssafy.TogetherBuyMain.shop.repository.product.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GroupBuyingBoardService {

    private final int SIZE = 10;
    private final Sort SORT = Sort.by(Sort.Direction.DESC, "updatedAt");

    private final S3Util s3Util;
    private final JwtUtil jwtUtil;
    private final MemberUtil memberUtil;

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final GroupBuyingBoardRepository groupBuyingBoardRepository;
    private final GroupBuyingBoardLikeRepository groupBuyingBoardLikeRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    @Transactional
    public GroupBuyingBoardRegisterResponseDTO registerGroupBuyingBoard(
            @RequestHeader("Authorization") String authorizationHeader,
            GroupBuyingBoardRegisterRequestDTO requestDTO,
            List<MultipartFile> images) {
        String accessToken = jwtUtil.extractToken(authorizationHeader);
        Member member = memberUtil.getMember(accessToken);
        Product product = productRepository.getReferenceById(requestDTO.productId());
        GroupBuyingBoard newGroupBuyingBoard = createGroupBuyingBoard(member, requestDTO, product);

        if(images == null || images.isEmpty())
            return new GroupBuyingBoardRegisterResponseDTO(newGroupBuyingBoard.getGroupBuyingBoardId());
        List<GroupBuyingBoardImage> groupBuyingBoardImages = updateImageList(images, newGroupBuyingBoard, member);

        newGroupBuyingBoard = groupBuyingBoardRepository.save(newGroupBuyingBoard);
        newGroupBuyingBoard.getImages().addAll(groupBuyingBoardImages);
        return new GroupBuyingBoardRegisterResponseDTO(newGroupBuyingBoard.getGroupBuyingBoardId());
    }

    @Transactional
    public PaginatedResponseDTO<GroupBuyingBoardResponseDTO> getGroupBuyingBoards(int page, Long categoryId, Long subCategoryId) {
        // Category와 SubCategory 유효성 검증
        Page<GroupBuyingBoard> boardPage;
        if (!categoryRepository.existsById(categoryId)) {
            boardPage = getGroupBuyingBoardPage(page);
        }
        else if (!subCategoryRepository.existsById(subCategoryId)) {
            boardPage = getGroupBuyingBoardPage(page, categoryId);
        }
        else {
            boardPage = getGroupBuyingBoardPage(page, categoryId, subCategoryId);
        }

        List<GroupBuyingBoardResponseDTO> boardList = getFreeBoardResponseDTOList(boardPage);

        return new PaginatedResponseDTO<>(
                boardList,
                boardPage.getNumber(),
                boardPage.getTotalPages(),
                boardPage.getTotalElements(),
                boardPage.getSize()
        );
    }

    @Transactional
    public GroupBuyingBoardDetailDTO getGroupBuyingBoard(Long boardId) {
        GroupBuyingBoard groupBuyingBoard = groupBuyingBoardRepository.getReferenceById(boardId);
        return GroupBuyingBoardDetailDTO.of(groupBuyingBoard);
    }

    @Transactional
    public GroupBuyingBoardWriterDTO getGroupBuyingBoardWriter(Long boardId) {
        GroupBuyingBoard groupBuyingBoard = groupBuyingBoardRepository.getReferenceById(boardId);
        return GroupBuyingBoardWriterDTO.of(groupBuyingBoard);
    }

    @Transactional
    public GroupBuyingBoardProductDTO getGroupBuyingBoardProduct(Long boardId) {
        GroupBuyingBoard groupBuyingBoard = groupBuyingBoardRepository.getReferenceById(boardId);
        return GroupBuyingBoardProductDTO.of(groupBuyingBoard);
    }

    @Transactional
    public GroupBuyingBoardUpdateResponseDTO updateGroupBuyingBoard(
            @RequestHeader("Authorization") String authorizationHeader,
            Long boardId,
            GroupBuyingBoardUpdateRequestDTO requestDTO,
            List<MultipartFile> newImages) {
        String accessToken = jwtUtil.extractToken(authorizationHeader);
        Member member = memberUtil.getMember(accessToken);
        GroupBuyingBoard groupBuyingBoard = groupBuyingBoardRepository.getReferenceById(boardId);

        if(isDifferentMember(member, groupBuyingBoard.getCreator())){
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        // 새 이미지 등록 및 삭제
        updateImageList(newImages, groupBuyingBoard, member);

        // 공구 게시글 업데이트
        groupBuyingBoard.updateTitleAndContent(requestDTO.groupBuyingBoardTitle(), requestDTO.groupBuyingBoardContent());

        return GroupBuyingBoardUpdateResponseDTO.of(groupBuyingBoard);
    }

    @Transactional
    public BooleanResponseDTO deleteGroupBuyingBoard(
            @RequestHeader("Authorization") String authorizationHeader,
            Long boardId) {
        String accessToken = jwtUtil.extractToken(authorizationHeader);
        Member member = memberUtil.getMember(accessToken);
        GroupBuyingBoard groupBuyingBoard = groupBuyingBoardRepository.getReferenceById(boardId);

        if (isDifferentMember(member, groupBuyingBoard.getCreator())) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }

        deleteImage(groupBuyingBoard.getImages());
        groupBuyingBoardRepository.delete(groupBuyingBoard);
        return new BooleanResponseDTO(true);
    }

    @Transactional
    public BoardLikeResponseDTO likeFreeBoard(
            @RequestHeader("Authorization") String authorizationHeader,
            Long boardId) {
        String accessToken = jwtUtil.extractToken(authorizationHeader);
        Member member = memberUtil.getMember(accessToken);
        GroupBuyingBoard groupBuyingBoard = groupBuyingBoardRepository.getReferenceById(boardId);

        boolean isLiked = groupBuyingBoardLikeRepository.isLiked(member, groupBuyingBoard);
        toggleLikeStatus(member, groupBuyingBoard, isLiked);

        return new BoardLikeResponseDTO(groupBuyingBoard.getLikes());
    }

    private GroupBuyingBoard createGroupBuyingBoard(
            Member member,
            GroupBuyingBoardRegisterRequestDTO requestDTO,
            Product product) {

        LocalDateTime now = LocalDateTime.now();
        GroupBuyingBoard newGroupBuyingBoard = new GroupBuyingBoard(
                null,
                requestDTO.title(),
                requestDTO.content(),
                0L, 0L,
                now, now,
                member, null,
                product, new ArrayList<>(), new ArrayList<>(),
                null
        );

        newGroupBuyingBoard = groupBuyingBoardRepository.save(newGroupBuyingBoard);

        member.getGroupBuyingBoards().add(newGroupBuyingBoard);

        return newGroupBuyingBoard;
    }

    private List<GroupBuyingBoardImage> updateImageList(
            List<MultipartFile> images,
            GroupBuyingBoard groupBuyingBoard,
            Member member) {
        deleteImage(groupBuyingBoard.getImages());
        if(!images.isEmpty()){
            List<GroupBuyingBoardImage> imageList = images.stream()
                    .map(image -> registerImage(image, groupBuyingBoard, member))
                    .toList();
            return groupBuyingBoard.getImages().addAll(imageList) ? imageList : new ArrayList<>();
        }

        return new ArrayList<>();
    }

    private GroupBuyingBoardImage registerImage(
            MultipartFile image,
            GroupBuyingBoard groupBuyingBoard,
            Member member) {
        String url = s3Util.uploadFile(image, member.getMemberId());
        return new GroupBuyingBoardImage(null, url, FileUtil.createFileName(image.getOriginalFilename()), image.getContentType(), groupBuyingBoard);
    }

    private void deleteImage(List<GroupBuyingBoardImage> existingImages) {
        for (GroupBuyingBoardImage image : existingImages) {
            s3Util.deleteFile(image.getFileName());
        }
        existingImages.clear(); // 연관 관계에서 삭제
    }

    private Page<GroupBuyingBoard> getGroupBuyingBoardPage(int page, Long categoryId, Long subCategoryId) {
        PageRequest pageable = PageRequest.of(page, SIZE, SORT);
        return groupBuyingBoardRepository.findByProduct_SubCategory_Category_CategoryIdAndProduct_SubCategory_Id(categoryId, subCategoryId, pageable);
    }

    private Page<GroupBuyingBoard> getGroupBuyingBoardPage(int page, Long categoryId) {
        PageRequest pageable = PageRequest.of(page, SIZE, SORT);
        return groupBuyingBoardRepository.findByProduct_SubCategory_Category_CategoryId(categoryId, pageable);
    }
    private Page<GroupBuyingBoard> getGroupBuyingBoardPage(int page) {
        PageRequest pageable = PageRequest.of(page, SIZE, SORT);
        return groupBuyingBoardRepository.findAll(pageable);
    }

    private List<GroupBuyingBoardResponseDTO> getFreeBoardResponseDTOList(Page<GroupBuyingBoard> boardPage) {
        return boardPage.getContent().stream()
                .map(GroupBuyingBoardResponseDTO::of)
                .toList();
    }

    private void toggleLikeStatus(Member member, GroupBuyingBoard groupBuyingBoard, boolean isLike) {
        if(!isLike) {
            groupBuyingBoardLikeRepository.like(member, groupBuyingBoard);
        } else {
            groupBuyingBoardLikeRepository.unlike(member, groupBuyingBoard);
        }
    }

    private boolean isDifferentMember(Member member, Member member2) {
        return !Objects.equals(member.getMemberId(), member2.getMemberId());
    }
}
