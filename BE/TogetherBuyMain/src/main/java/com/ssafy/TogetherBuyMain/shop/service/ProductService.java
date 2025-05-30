package com.ssafy.TogetherBuyMain.shop.service;

import com.ssafy.TogetherBuyMain.shop.dto.product.response.CategoryResponseDTO;
import com.ssafy.TogetherBuyMain.shop.dto.product.response.ProductDetailResponseDTO;
import com.ssafy.TogetherBuyMain.shop.dto.product.response.ProductLikeResponseDTO;
import com.ssafy.TogetherBuyMain.shop.dto.product.response.ProductListResponseDto;

import java.util.List;

public interface ProductService {
    ProductListResponseDto findAllProducts(Long categoryId, Long subCategoryId);
    ProductLikeResponseDTO toggleLike(Long productId, String authorizationHeader);  // 반환 타입을 ProductLikeResponse로 변경
    List<CategoryResponseDTO> getAllCategories();
    ProductDetailResponseDTO findProductDetailById(Long productId);
}