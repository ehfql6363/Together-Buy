package com.ssafy.TogetherBuyMain.community.controller;

import com.ssafy.TogetherBuyMain.community.dto.freeboard.HotFreeBoardResponseDTO;
import com.ssafy.TogetherBuyMain.community.dto.groupBuyingBoard.ExpiringGroupBuyingBoardResponseDTO;
import com.ssafy.TogetherBuyMain.community.dto.groupBuyingBoard.AppliedGroupBuyingBoardResponseDTO;
import com.ssafy.TogetherBuyMain.community.service.MainService;
import com.ssafy.TogetherBuyMain.shop.dto.product.WishedProductResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    // 참여중인 공구 게시글 목록 조회
    @GetMapping("/applied")
    public ResponseEntity<AppliedGroupBuyingBoardResponseDTO> getAppliedBoards(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        AppliedGroupBuyingBoardResponseDTO response = mainService.getAppliedGroupBuyingBoards(authorizationHeader);
        return ResponseEntity.ok(response);
    }

    // 임박한 공동 구매 게시글 목록 조회
    @GetMapping("/expiring")
    public ResponseEntity<ExpiringGroupBuyingBoardResponseDTO> getExpiringBoards(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        ExpiringGroupBuyingBoardResponseDTO response = mainService.getExpiringBoards(authorizationHeader);
        return ResponseEntity.ok(response);
    }

    // 관심 상품 목록 조회
    @GetMapping("/wished")
    public ResponseEntity<WishedProductResponseDTO> getWishedProducts(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        WishedProductResponseDTO response = mainService.getWishedProducts(authorizationHeader);
        return ResponseEntity.ok(response);
    }

    // HOT 게시글 조회
    @GetMapping("/hot")
    public ResponseEntity<HotFreeBoardResponseDTO> getHotFreeBoards() {
        HotFreeBoardResponseDTO response = mainService.getHotFreeBoards();
        return ResponseEntity.ok(response);
    }

}
