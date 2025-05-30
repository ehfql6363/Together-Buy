package com.ssafy.TogetherBuyMain.shop.dto.product.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO {
    private Long categoryId;
    private String categoryName;
    private String categoryImage;  // 추가된 필드
    private List<SubCategory> subCategories;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SubCategory {
        private Long subCategoryId;  // 변경된 필드 이름
        private String subCategoryName;  // 변경된 필드 이름
        private String subCategoryImage;  // 추가된 필드
    }
}
