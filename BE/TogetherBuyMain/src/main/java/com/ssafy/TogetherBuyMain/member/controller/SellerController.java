package com.ssafy.TogetherBuyMain.member.controller;

import com.ssafy.TogetherBuyMain.member.dto.request.RegisterSellerRequestDTO;
import com.ssafy.TogetherBuyMain.security.email.dto.request.EmailVerificationRequestDTO;
import com.ssafy.TogetherBuyMain.security.email.dto.response.EmailVerificationResponseDTO;
import com.ssafy.TogetherBuyMain.security.email.emailService.EmailService;
import com.ssafy.TogetherBuyMain.shop.dto.order.OrderDetailResponseDTO;
import com.ssafy.TogetherBuyMain.shop.dto.order.OrderStatusListResponseDTO;
import com.ssafy.TogetherBuyMain.shop.dto.product.*;
import com.ssafy.TogetherBuyMain.member.service.SellerService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/seller")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    // 이메일 인증 전송
    @PostMapping("/emails/verify")
    public ResponseEntity<Void> sendCode(@RequestHeader("Authorization") String authorization) {
        sellerService.sendCode(authorization);
        return ResponseEntity.ok().build();
    }

    // 이메일 인증 검증
    @PostMapping("/emails/verify/confirm")
    public ResponseEntity<EmailVerificationResponseDTO> verifyCode(
            @RequestHeader("Authorization") String authorization,
            @RequestBody EmailVerificationRequestDTO request) {
        EmailVerificationResponseDTO response = sellerService.verifyCode(authorization, request.verificationCode());
        return ResponseEntity.ok(response);
    }


    // 판매자 등록
    @PostMapping("")
    public ResponseEntity<Void> registerSeller(
            @RequestHeader("Authorization") String authorization,
            @RequestPart("request") RegisterSellerRequestDTO request,
            HttpServletResponse response) {
        sellerService.registerSeller(authorization, request, response);
        return ResponseEntity.ok().build();
    }

    // 상품 등록
    @PostMapping("/product")
    public ResponseEntity<RegisterProductResponseDTO> registerProduct(
            @RequestHeader("Authorization") String authorization,
            @RequestPart("request") RegisterProductRequestDTO request,
            @RequestPart(name = "productImages", required = false) List<MultipartFile> productImages,
            @RequestPart(name = "productDetailImage", required = false) MultipartFile detailImage) {
        RegisterProductResponseDTO response = sellerService.registerProduct(authorization, request, productImages, detailImage);
        return ResponseEntity.ok(response);
    }

    // 등록한 상품 목록 조회
    @GetMapping("/products")
    public ResponseEntity<List<RetrieveSellersProductsResponseDTO>> getSellersProducts(
            @RequestHeader("Authorization") String authorization) {
        List<RetrieveSellersProductsResponseDTO> response = sellerService.getSellersProducts(authorization);
        return ResponseEntity.ok(response);
    }

    // 상품에 대한 주문 내역 조회
    @GetMapping("/product/{productId}/orders")
    public ResponseEntity<RetrieveOrdersByProductResponseDTO> getOrdersByProduct(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("productId") Long productId) {
        RetrieveOrdersByProductResponseDTO response = sellerService.getOrdersByProduct(authorization, productId);
        return ResponseEntity.ok(response);
    }

    // 주문서 상세조회
    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderDetailResponseDTO> getOrderDetail(
            @PathVariable("orderId") Long orderId) {
        OrderDetailResponseDTO response = sellerService.getOrderDetail(orderId);
        return ResponseEntity.ok(response);
    }

    // 배송 내역 조회
    @GetMapping("/orders/delivery/{status}")
    public ResponseEntity<OrderStatusListResponseDTO> getOrderStatuses(
            @PathVariable("status") String status) {
        OrderStatusListResponseDTO response = sellerService.getOrderStatusList(status);
        return ResponseEntity.ok(response);
    }

    // 주문 배송
    @PostMapping("/order/{orderId}/delivery/{wayBillNumber}")
    public ResponseEntity<Void> changeDeliveryStatus(
            @PathVariable("orderId") Long orderId,
            @PathVariable("wayBillNumber") String wayBillNumber) {
        sellerService.changeDeliveryStatus(orderId, wayBillNumber);
        return ResponseEntity.ok().build();
    }

    // 상품 수정 GET
    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductModifyResponseDTO> getProductDetail(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("productId") Long productId) {
        ProductModifyResponseDTO response = sellerService.getProductDetail(authorization, productId);
        return ResponseEntity.ok(response);
    }

    // 상품 수정 PUT
    @PutMapping("/product/{productId}")
    public ResponseEntity<Void> updateProduct(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("productId") Long productId,
            @RequestPart("request") ProductUpdateRequestDTO request,
            @RequestPart(name = "productImages", required = false) List<MultipartFile> productImages,
            @RequestPart(name = "productDetailImage", required = false) MultipartFile productDetailImage) {
        sellerService.updateProduct(authorization, productId, request, productImages, productDetailImage);
        return ResponseEntity.ok().build();
    }

    // 상품 삭제
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("productId") Long productId){
        sellerService.deleteProduct(authorization, productId);
        return ResponseEntity.ok().build();
    }

}
