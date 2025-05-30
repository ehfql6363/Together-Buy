package com.ssafy.TogetherBuyMain.global.config;

import com.ssafy.TogetherBuyMain.billing.entity.AdminPoint;
import com.ssafy.TogetherBuyMain.billing.repository.AdminPointRepository;
import com.ssafy.TogetherBuyMain.shop.entity.product.Category;
import com.ssafy.TogetherBuyMain.shop.entity.product.SubCategory;
import com.ssafy.TogetherBuyMain.shop.repository.category.CategoryRepository;
import com.ssafy.TogetherBuyMain.shop.repository.category.SubCategoryRepository;
import java.util.Arrays;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final AdminPointRepository adminPointRepository;

    @Override
    public void run(String... args) throws Exception {
        if (adminPointRepository.count() == 0) {
            AdminPoint adminPoint = AdminPoint.builder().point(0L).build();
            adminPointRepository.save(adminPoint);
            System.out.println("AdminPoint initial data insertion complete.");
        }

        if(categoryRepository.count() > 0 || subCategoryRepository.count() > 0){
            // Category 데이터 업데이트
            List<Category> allCategories = categoryRepository.findAll(); // DB의 모든 카테고리를 가져옵니다.
            if (allCategories.isEmpty()) {
                System.out.println("No categories found in the database. Skipping category update.");
            } else {
                for (Category category : allCategories) {
                    if(category.getCategoryImage().equals(getUpdatedImageUrlForCategory(category))) continue;
                    category.updateCategoryImage(getUpdatedImageUrlForCategory(category)); // 이미지를 설정
                    categoryRepository.save(category); // 업데이트를 다시 저장
                }
                System.out.println("Category data updated successfully!");
            }

            // SubCategory 데이터 업데이트
            List<SubCategory> allSubCategories = subCategoryRepository.findAll(); // DB의 모든 서브카테고리를 가져옵니다.
            if (allSubCategories.isEmpty()) {
                System.out.println("No subcategories found in the database. Skipping subcategory update.");
            } else {
                for (SubCategory subCategory : allSubCategories) {
                    if(subCategory.getSubCategoryImage().equals(getUpdatedImageUrlForSubCategory(subCategory))) continue;
                    subCategory.updateSubCategoryImage(getUpdatedImageUrlForSubCategory(subCategory)); // 이미지를 설정
                    subCategoryRepository.save(subCategory); // 업데이트를 다시 저장
                }
                System.out.println("Subcategory data updated successfully!");
            }
        }

        if (categoryRepository.count() == 0) {
            System.out.println("Initial data insertion starting...");
            Category category1 = Category.builder().categoryName("정육/계란").categoryImage(
                            "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%A0%95%EC%9C%A1%EA%B3%84%EB%9E%80.webp")
                    .build();
            Category category2 = Category.builder().categoryName("과일/채소").categoryImage(
                            "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EA%B3%BC%EC%9D%BC%EC%B1%84%EC%86%8C.png")
                    .build();
            Category category3 = Category.builder().categoryName("냉장/냉동/간편식").categoryImage(
                            "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%83%89%EB%8F%99%EB%83%89%EC%9E%A5%EA%B0%84%ED%8E%B8%EC%8B%9D.png")
                    .build();
            Category category4 = Category.builder().categoryName("커피/음료").categoryImage(
                            "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%BB%A4%ED%94%BC%EC%9D%8C%EB%A3%8C.png")
                    .build();
            Category category5 = Category.builder().categoryName("간식/베이커리").categoryImage(
                            "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EA%B0%84%EC%8B%9D%EB%B2%A0%EC%9D%B4%EC%BB%A4%EB%A6%AC.png")
                    .build();

            categoryRepository.saveAll(Arrays.asList(category1, category2, category3, category4, category5));

            System.out.println("Category initial data insertion complete.");

            if (subCategoryRepository.count() == 0) {
                // 정육/계란
                SubCategory sub1_1 = SubCategory.builder().category(category1).name("국내산 소고기").subCategoryImage(
                                "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%86%8C%EA%B3%A0%EA%B8%B0.png")
                        .build();
                SubCategory sub1_2 = SubCategory.builder().category(category1)
                        .name("돼지 고기")
                        .subCategoryImage(
                                "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%8F%BC%EC%A7%80%EA%B3%A0%EA%B8%B0.png")
                        .build();
                SubCategory sub1_3 = SubCategory.builder().category(category1).name("닭고기").subCategoryImage(
                                "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%8B%AD%EA%B3%A0%EA%B8%B0.png")
                        .build();
                SubCategory sub1_4 = SubCategory.builder().category(category1).name("계란")
                        .subCategoryImage("https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EA%B3%84%EB%9E%80.png")
                        .build();
                SubCategory sub1_5 = SubCategory.builder().category(category1).name("오리고기").subCategoryImage(
                                "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%98%A4%EB%A6%AC%EA%B3%A0%EA%B8%B0.png")
                        .build();
                subCategoryRepository.saveAll(Arrays.asList(sub1_1, sub1_2, sub1_3, sub1_4, sub1_5));

                // 과일/채소
                SubCategory sub2_1 = SubCategory.builder().category(category2).name("제철 과일").subCategoryImage(
                                "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%A0%9C%EC%B2%A0%EA%B3%BC%EC%9D%BC.png")
                        .build();
                SubCategory sub2_2 = SubCategory.builder().category(category2).name("국내산 과일").subCategoryImage(
                                "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EA%B5%AD%EB%82%B4%EC%82%B0%EA%B3%BC%EC%9D%BC.png")
                        .build();
                SubCategory sub2_3 = SubCategory.builder().category(category2).name("수입 과일").subCategoryImage(
                                "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%88%98%EC%9E%85%EA%B3%BC%EC%9D%BC.png")
                        .build();
                SubCategory sub2_4 = SubCategory.builder().category(category2).name("뿌리 채소").subCategoryImage(
                                "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%BF%8C%EB%A6%AC%EC%B1%84%EC%86%8C.png")
                        .build();
                SubCategory sub2_5 = SubCategory.builder().category(category2).name("쌈 채소").subCategoryImage(
                                "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%8C%88%EC%B1%84%EC%86%8C.png")
                        .build();
                subCategoryRepository.saveAll(Arrays.asList(sub2_1, sub2_2, sub2_3, sub2_4, sub2_5));

                // 냉장/냉동/간편식
                SubCategory sub3_1 = SubCategory.builder().category(category3).name("즉석조리식품").subCategoryImage(
                                "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%A6%89%EC%84%9D%EC%A1%B0%EB%A6%AC%EC%8B%9D%ED%92%88.png")
                        .build();
                SubCategory sub3_2 = SubCategory.builder().category(category3).name("냉동만두/튀김").subCategoryImage(
                                "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%83%89%EB%8F%99%EB%A7%8C%EB%91%90%ED%8A%80%EA%B9%80.png")
                        .build();
                SubCategory sub3_3 = SubCategory.builder().category(category3).name("냉동피자").subCategoryImage(
                                "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%83%89%EB%8F%99%ED%94%BC%EC%9E%90.png")
                        .build();
                SubCategory sub3_4 = SubCategory.builder().category(category3).name("아이스크림").subCategoryImage(
                                "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%95%84%EC%9D%B4%EC%8A%A4%ED%81%AC%EB%A6%BC.png")
                        .build();
                SubCategory sub3_5 = SubCategory.builder().category(category3).name("도시락").subCategoryImage(
                                "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%8F%84%EC%8B%9C%EB%9D%BD.png")
                        .build();
                subCategoryRepository.saveAll(Arrays.asList(sub3_1, sub3_2, sub3_3, sub3_4, sub3_5));

                // 커피/음료
                SubCategory sub4_1 = SubCategory.builder().category(category4).name("커피원두").subCategoryImage(
                                "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%BB%A4%ED%94%BC%EC%9B%90%EB%91%90.png")
                        .build();
                SubCategory sub4_2 = SubCategory.builder().category(category4).name("생수/탄산수").subCategoryImage(
                                "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%83%9D%EC%88%98%ED%83%84%EC%82%B0%EC%88%98.png")
                        .build();
                SubCategory sub4_3 = SubCategory.builder().category(category4).name("과일주스").subCategoryImage(
                                "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EA%B3%BC%EC%9D%BC%EC%A3%BC%EC%8A%A4.png")
                        .build();
                SubCategory sub4_4 = SubCategory.builder().category(category4).name("탄산음료").subCategoryImage(
                                "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%ED%83%84%EC%82%B0%EC%9D%8C%EB%A3%8C.png")
                        .build();
                SubCategory sub4_5 = SubCategory.builder().category(category4).name("전통차").subCategoryImage(
                                "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%A0%84%ED%86%B5%EC%B0%A8.png")
                        .build();
                subCategoryRepository.saveAll(Arrays.asList(sub4_1, sub4_2, sub4_3, sub4_4, sub4_5));

                // 간식/베이커리
                SubCategory sub5_1 = SubCategory.builder().category(category5).name("빵")
                        .subCategoryImage("https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%B9%B5.png").build();
                SubCategory sub5_2 = SubCategory.builder().category(category5).name("과자/스낵").subCategoryImage(
                                "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EA%B3%BC%EC%9E%90%EC%8A%A4%EB%82%B5.png")
                        .build();
                SubCategory sub5_3 = SubCategory.builder().category(category5).name("초콜릿/사탕").subCategoryImage(
                                "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%B4%88%EC%BD%9C%EB%A6%BF%EC%82%AC%ED%83%95.png")
                        .build();
                SubCategory sub5_4 = SubCategory.builder().category(category5).name("케이크").subCategoryImage(
                                "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%BC%80%EC%9D%B4%ED%81%AC.png")
                        .build();
                SubCategory sub5_5 = SubCategory.builder().category(category5).name("떡")
                        .subCategoryImage("https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%96%A1.png").build();
                subCategoryRepository.saveAll(Arrays.asList(sub5_1, sub5_2, sub5_3, sub5_4, sub5_5));
            } else {
                System.out.println("SubCategory initial data insertion skipped.");
            }
        }

        System.out.println("Initial data insertion complete.");


    }

    /**
     * Category 엔티티의 새로운 이미지 URL을 반환하는 메서드
     */
    private String getUpdatedImageUrlForCategory(Category category) {
        // categoryName을 기준으로 이미지 URL 설정 (예시로 작성)
        return switch (category.getCategoryName()) {
            case "정육/계란" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%A0%95%EC%9C%A1%EA%B3%84%EB%9E%80.webp";
            case "과일/채소" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EA%B3%BC%EC%9D%BC%EC%B1%84%EC%86%8C.png";
            case "냉장/냉동/간편식" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%83%89%EB%8F%99%EB%83%89%EC%9E%A5%EA%B0%84%ED%8E%B8%EC%8B%9D.png";
            case "커피/음료" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%BB%A4%ED%94%BC%EC%9D%8C%EB%A3%8C.png";
            case "간식/베이커리" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EA%B0%84%EC%8B%9D%EB%B2%A0%EC%9D%B4%EC%BB%A4%EB%A6%AC.png";
            default -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%A0%95%EC%9C%A1%EA%B3%84%EB%9E%80.webp";
        };
    }

    /**
     * SubCategory 엔티티의 새로운 이미지 URL을 반환하는 메서드
     */
    private String getUpdatedImageUrlForSubCategory(SubCategory subCategory) {
        // subCategoryName을 기준으로 이미지 URL 설정 (예시로 작성)
        return switch (subCategory.getName()) {
            case "국내산 소고기" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%86%8C%EA%B3%A0%EA%B8%B0.png";
            case "돼지 고기" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%8F%BC%EC%A7%80%EA%B3%A0%EA%B8%B0.png";
            case "닭고기" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%8B%AD%EA%B3%A0%EA%B8%B0.png";
            case "계란" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EA%B3%84%EB%9E%80.png";
            case "오리고기" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%98%A4%EB%A6%AC%EA%B3%A0%EA%B8%B0.png";
            case "제철 과일" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%A0%9C%EC%B2%A0%EA%B3%BC%EC%9D%BC.png";
            case "국내산 과일" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EA%B5%AD%EB%82%B4%EC%82%B0%EA%B3%BC%EC%9D%BC.png";
            case "수입 과일" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%88%98%EC%9E%85%EA%B3%BC%EC%9D%BC.png";
            case "뿌리 채소" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%BF%8C%EB%A6%AC%EC%B1%84%EC%86%8C.png";
            case "쌈 채소" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%8C%88%EC%B1%84%EC%86%8C.png";
            case "즉석조리식품" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%A6%89%EC%84%9D%EC%A1%B0%EB%A6%AC%EC%8B%9D%ED%92%88.png";
            case "냉동만두/튀김" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%83%89%EB%8F%99%EB%A7%8C%EB%91%90%ED%8A%80%EA%B9%80.png";
            case "냉동피자" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%83%89%EB%8F%99%ED%94%BC%EC%9E%90.png";
            case "아이스크림" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%95%84%EC%9D%B4%EC%8A%A4%ED%81%AC%EB%A6%BC.png";
            case "도시락" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%8F%84%EC%8B%9C%EB%9D%BD.png";
            case "커피원두" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%BB%A4%ED%94%BC%EC%9B%90%EB%91%90.png";
            case "생수/탄산수" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%83%9D%EC%88%98%ED%83%84%EC%82%B0%EC%88%98.png";
            case "과일주스" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EA%B3%BC%EC%9D%BC%EC%A3%BC%EC%8A%A4.png";
            case "탄산음료" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%ED%83%84%EC%82%B0%EC%9D%8C%EB%A3%8C.png";
            case "전통차" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%A0%84%ED%86%B5%EC%B0%A8.png";
            case "빵" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%B9%B5.png";
            case "과자/스낵" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EA%B3%BC%EC%9E%90%EC%8A%A4%EB%82%B5.png";
            case "초콜릿/사탕" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%B4%88%EC%BD%9C%EB%A6%BF%EC%82%AC%ED%83%95.png";
            case "케이크" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%BC%80%EC%9D%B4%ED%81%AC.png";
            case "떡" -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%96%A1.png";
            default -> "https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%96%A1.png";
        };
    }
}

