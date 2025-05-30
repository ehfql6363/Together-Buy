package com.ssafy.TogetherBuyMain.shop.service;

import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.member.entity.MemberImage;
import com.ssafy.TogetherBuyMain.member.entity.Seller;
import com.ssafy.TogetherBuyMain.member.repository.MemberRepository;
import com.ssafy.TogetherBuyMain.security.jwt.service.JwtService;
import com.ssafy.TogetherBuyMain.security.jwt.util.JwtUtil;
import com.ssafy.TogetherBuyMain.shop.dto.product.response.CategoryResponseDTO;
import com.ssafy.TogetherBuyMain.shop.dto.product.response.ProductDetailResponseDTO;
import com.ssafy.TogetherBuyMain.shop.dto.product.response.ProductLikeResponseDTO;
import com.ssafy.TogetherBuyMain.shop.dto.product.response.ProductListResponseDto;
import com.ssafy.TogetherBuyMain.shop.entity.common.Form;
import com.ssafy.TogetherBuyMain.shop.entity.common.ProductImage;
import com.ssafy.TogetherBuyMain.shop.entity.product.Category;
import com.ssafy.TogetherBuyMain.shop.entity.product.Product;
import com.ssafy.TogetherBuyMain.shop.entity.product.SubCategory;
import com.ssafy.TogetherBuyMain.shop.exception.ProductErrorCode;
import com.ssafy.TogetherBuyMain.shop.exception.ProductException;
import com.ssafy.TogetherBuyMain.shop.entity.order.Order;
import com.ssafy.TogetherBuyMain.shop.repository.category.CategoryRepository;
import com.ssafy.TogetherBuyMain.shop.repository.category.SubCategoryRepository;
import com.ssafy.TogetherBuyMain.shop.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final JwtService jwtService;

    @Override
    public ProductListResponseDto findAllProducts(Long categoryId, Long subCategoryId) {
        List<Product> products;

        // 전체 상품 조회 (categoryId와 subCategoryId가 모두 0인 경우)
        if (categoryId == 0 && subCategoryId == 0) {
            products = productRepository.findAll();
        }
        // 특정 카테고리 내 전체 상품 조회 (categoryId는 특정 값이고 subCategoryId가 0인 경우)
        else if (categoryId != 0 && subCategoryId == 0) {
            products = productRepository.findByCategoryId(categoryId);
        }
        // 특정 카테고리와 서브 카테고리의 상품 조회
        else {
            validateCategory(categoryId, subCategoryId);  // 카테고리 존재 여부 확인
            products = productRepository.findByCategoryAndSubCategory(categoryId, subCategoryId);
        }

        if (products.isEmpty()) {
            throw new ProductException(ProductErrorCode.SEARCH_NO_RESULTS);
        }

        List<ProductListResponseDto.ProductInfo> productInfos = products.stream()
                .map(this::toProductInfo)
                .collect(Collectors.toList());

        return new ProductListResponseDto(productInfos);
    }

//    @Override
//    public ProductListResponseDto findAllProducts(Long categoryId, Long subCategoryId) {
//
//        if (categoryId == null || subCategoryId == null) {
//            throw new ProductException(ProductErrorCode.INVALID_PARAMETER);
//        }
//        // 카테고리 존재 여부 확인
//        validateCategory(categoryId, subCategoryId);
//
//        // 상품 목록 조회
//        List<Product> products = productRepository.findByCategoryAndSubCategory(categoryId, subCategoryId);
//        if (products.isEmpty()) {
//            throw new ProductException(ProductErrorCode.SEARCH_NO_RESULTS);
//        }
//
//        List<ProductListResponseDto.ProductInfo> productInfos = products.stream()
//                .map(this::toProductInfo)
//                .collect(Collectors.toList());
//
//        return new ProductListResponseDto(productInfos);
//    }

    @Transactional
    @Override
    public ProductLikeResponseDTO toggleLike(Long productId, String authorizationHeader) {

        String accessToken = jwtService.extractToken(authorizationHeader);

        Long memberId = jwtUtil.getMemberIdFromToken(accessToken);

        if (productId == null || memberId == null) {
            throw new ProductException(ProductErrorCode.INVALID_PARAMETER);
        }

        // 상품 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        // 회원 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.MEMBER_NOT_FOUND));

        // 현재 찜 상태에 따라 토글
        if (member.getWishedProducts().contains(product)) {
            // 이미 찜한 상태면 제거
            member.getWishedProducts().remove(product);
        } else {
            // 찜하지 않은 상태면 추가
            member.getWishedProducts().add(product);
        }

        // 영속성 컨텍스트 변경사항 반영을 위해 flush
        memberRepository.flush();

        // 전체 찜 개수 반환 (Product의 wishingMembers size 활용)
        return new ProductLikeResponseDTO(product.getWishingMembers().size());
    }


    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new ProductException(ProductErrorCode.CATEGORY_IS_EMPTY);
        }

        return categories.stream()
                .map(this::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDetailResponseDTO findProductDetailById(Long productId) {
        if (productId == null) {
            throw new ProductException(ProductErrorCode.INVALID_PARAMETER);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        return toProductDetailResponse(product);
    }

    private ProductListResponseDto.ProductInfo toProductInfo(Product product) {
        return ProductListResponseDto.ProductInfo.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .unitAmount(product.getUnitAmount())
                .unitName(product.getUnitName())
                .pricePerUnit(product.getPricePerUnit())
                .productMainImage(product.getImages().isEmpty() ? null : product.getImages().get(0).getUrl())
                .build();
    }

    private CategoryResponseDTO toCategoryResponse(Category category) {
        List<CategoryResponseDTO.SubCategory> subCategories = category.getSubCategories().stream()
                .map(sub -> new CategoryResponseDTO.SubCategory(
                        sub.getId(),
                        sub.getName(),
                        sub.getSubCategoryImage()  // subCategoryImage를 매핑
                ))
                .collect(Collectors.toList());

        return new CategoryResponseDTO(
                category.getCategoryId(),
                category.getCategoryName(),
                category.getCategoryImage(),  // categoryImage를 매핑
                subCategories
        );
    }

    private ProductDetailResponseDTO toProductDetailResponse(Product product) {
        // 공동구매 게시글 정보 처리
        List<ProductDetailResponseDTO.GroupBuyingInfo> boards = product.getGroupBuyingBoards().stream()
                .map(board -> {
                    Form form = board.getForm() == null ? null : board.getForm();
                    return new ProductDetailResponseDTO.GroupBuyingInfo(
                            board.getGroupBuyingBoardId(),
                            board.getTitle(),
                            form == null ? null : form.getCurrentAmount(),
                            form == null ? null : form.getTotalAmount(),
                            form == null ? null : form.getMeetingLocation().buildFullAddress()
                    );
                })
                .collect(Collectors.toList());

        // 상품 이미지 처리 (첫 번째 이미지를 가져옴)
        List<String> images = product.getImages().stream()
                .map(ProductImage::getUrl)
                .toList();

        // 프로필 이미지 URL 처리
        String profileImageUrl = Optional.ofNullable(product.getSeller())
                .map(Seller::getMember)
                .map(Member::getProfileImage)
                .map(MemberImage::getUrl)
                .orElse(null);

        // 판매자 정보 처리
        ProductDetailResponseDTO.SellerInfo seller = Optional.ofNullable(product.getSeller())
                .map(s -> new ProductDetailResponseDTO.SellerInfo(
                        s.getSellerId(),
                        s.getBusinessName(),
                        profileImageUrl
                ))
                .orElse(new ProductDetailResponseDTO.SellerInfo(0L, "", null));

        // 카테고리 정보 처리
        ProductDetailResponseDTO.Category category = new ProductDetailResponseDTO.Category(
                product.getSubCategory().getCategory().getCategoryName(),  // mainCategory -> categoryName
                product.getSubCategory().getName()
        );

        // 구매된 수량 처리 (Order에서 수량 합산)
        Long currentAmount = product.getOrders().stream()
                .mapToLong(Order::getOrderQuantity)
                .sum();

        // 첫 번째 이미지를 기본 이미지로 설정 (이미지가 없으면 null)
        String productImage = images.isEmpty() ? null : images.get(0);

        // 반환값 생성
        return new ProductDetailResponseDTO(
                product.getProductId(),
                product.getProductName(),
                productImage,  // 첫 번째 이미지
                product.getDetailImage(),
                (long) product.getWishingMembers().size(),  // 찜한 개수
                product.getPrice(),
                product.getSalePrice(),
                product.getPricePerUnit(),
                product.getUnitAmount(),
                currentAmount,  // 구매된 수량
                product.getUnitName(),
                category,  // 카테고리 정보
                boards,  // 공구글
                seller,  // 판매자 정보
                product.getTotal()
        );
    }

    private void validateCategory(Long categoryId, Long subCategoryId) {

        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.SUBCATEGORY_NOT_FOUND));

        if (!subCategory.getCategory().getCategoryId().equals(categoryId)) {
            throw new ProductException(ProductErrorCode.INVALID_CATEGORY_SUBCATEGORY_COMBINATION);
        }
    }
}
