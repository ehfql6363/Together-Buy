package com.ssafy.TogetherBuyMain.community.service;

import com.ssafy.TogetherBuyMain.community.dto.freeboard.HotFreeBoardDTO;
import com.ssafy.TogetherBuyMain.community.dto.freeboard.HotFreeBoardResponseDTO;
import com.ssafy.TogetherBuyMain.community.dto.groupBuyingBoard.ExpiringGroupBuyingBoardDTO;
import com.ssafy.TogetherBuyMain.community.dto.groupBuyingBoard.ExpiringGroupBuyingBoardResponseDTO;
import com.ssafy.TogetherBuyMain.community.dto.groupBuyingBoard.AppliedGroupBuyingBoardDTO;
import com.ssafy.TogetherBuyMain.community.dto.groupBuyingBoard.AppliedGroupBuyingBoardResponseDTO;
import com.ssafy.TogetherBuyMain.community.repository.freeBoard.FreeBoardRepository;
import com.ssafy.TogetherBuyMain.community.repository.groupBuyingBoard.GroupBuyingBoardRepository;
import com.ssafy.TogetherBuyMain.shop.repository.common.ApplyRepository;
import com.ssafy.TogetherBuyMain.global.util.MemberUtil;
import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.security.jwt.util.JwtUtil;
import com.ssafy.TogetherBuyMain.shop.dto.product.WishedProductDTO;
import com.ssafy.TogetherBuyMain.shop.dto.product.WishedProductResponseDTO;
import com.ssafy.TogetherBuyMain.shop.entity.common.Apply;
import com.ssafy.TogetherBuyMain.shop.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainService {

    private final JwtUtil jwtUtil;
    private final MemberUtil memberUtil;

    private final ApplyRepository applyRepository;
    private final GroupBuyingBoardRepository groupBuyingBoardRepository;
    private final ProductRepository productRepository;
    private final FreeBoardRepository freeBoardRepository;

    @Transactional
    public AppliedGroupBuyingBoardResponseDTO getAppliedGroupBuyingBoards(String authorizationHeader) {
        String accessToken = jwtUtil.extractToken(authorizationHeader);
        Member member = memberUtil.getMember(accessToken);
        List<AppliedGroupBuyingBoardDTO> appliedGroupBuyingBoards = getAppliedGroupBuyingBoardsByMember(member);
        
        return new AppliedGroupBuyingBoardResponseDTO(appliedGroupBuyingBoards);
    }

    @Transactional
    public ExpiringGroupBuyingBoardResponseDTO getExpiringBoards(String authorizationHeader) {
        String accessToken = jwtUtil.extractToken(authorizationHeader);
        Member member = memberUtil.getMember(accessToken);
        List<Object[]> results = groupBuyingBoardRepository
                .findExpiringGroupBuyingBoards();

        List<ExpiringGroupBuyingBoardDTO> expiringBoards = results.stream()
                .map(ExpiringGroupBuyingBoardDTO::from)
                .collect(Collectors.toList());

        return new ExpiringGroupBuyingBoardResponseDTO(expiringBoards);
    }

//    @Transactional
//    public ExpiringGroupBuyingBoardResponseDTO getExpiringBoards(String authorizationHeader) {
//        String accessToken = jwtUtil.extractToken(authorizationHeader);
//        Member member = memberUtil.getMember(accessToken);
//        List<ExpiringGroupBuyingBoardDTO> expiringBoards = groupBuyingBoardRepository
//                .findExpiringGroupBuyingBoards();
//
//        return new ExpiringGroupBuyingBoardResponseDTO(expiringBoards);
//    }

    @Transactional
    public WishedProductResponseDTO getWishedProducts(String authorizationHeader) {
        String accessToken = jwtUtil.extractToken(authorizationHeader);
        Member member = memberUtil.getMember(accessToken);
        List<WishedProductDTO> wishedProducts = productRepository.findAllByWishMember(member);
        return new WishedProductResponseDTO(wishedProducts);
    }

    @Transactional
    public HotFreeBoardResponseDTO getHotFreeBoards() {
        List<HotFreeBoardDTO> hotFreeBoards = freeBoardRepository.findHotFreeBoard();
        return new HotFreeBoardResponseDTO(hotFreeBoards);
    }
    
    private List<AppliedGroupBuyingBoardDTO> getAppliedGroupBuyingBoardsByMember(Member member) {
        List<Apply> applies = applyRepository.findAllByMember(member);
        return applies.stream()
                .map(AppliedGroupBuyingBoardDTO::of)
                .toList();
    }
}
