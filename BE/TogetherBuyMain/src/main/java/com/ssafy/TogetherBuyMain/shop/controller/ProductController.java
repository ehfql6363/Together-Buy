package com.ssafy.TogetherBuyMain.shop.controller;

import com.ssafy.TogetherBuyMain.shop.dto.product.response.CategoryResponseDTO;
import com.ssafy.TogetherBuyMain.shop.dto.product.response.ProductDetailResponseDTO;
import com.ssafy.TogetherBuyMain.shop.dto.product.response.ProductLikeResponseDTO;
import com.ssafy.TogetherBuyMain.shop.dto.product.response.ProductListResponseDto;
import com.ssafy.TogetherBuyMain.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // 상품 리스트 조회(카테고리 상관없이 모든 상품 조회)
    @GetMapping("/products/category/{categoryId}/sub-category/{subCategoryId}")
    public ResponseEntity<ProductListResponseDto> getAllProducts(
            @PathVariable Long categoryId,
            @PathVariable Long subCategoryId,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        ProductListResponseDto response = productService.findAllProducts(categoryId, subCategoryId);
        return ResponseEntity.ok(response);
    }

    // 상품 좋아요 토글 진행
    @PostMapping("/like/product/{productId}")
    public ResponseEntity<ProductLikeResponseDTO> toggleProductLike(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        return ResponseEntity.ok(productService.toggleLike(productId, authorizationHeader));
    }

    // 카테고리 조회
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponseDTO>> getCategories(@RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(productService.getAllCategories());
    }

    // 상품 상세정보
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDetailResponseDTO> getProductDetail(@PathVariable Long productId,
                                                                     @RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(productService.findProductDetailById(productId));
    }

}