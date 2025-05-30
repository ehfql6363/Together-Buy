package com.ssafy.TogetherBuyMain.shop.repository.product;

import com.ssafy.TogetherBuyMain.member.entity.Seller;
import com.ssafy.TogetherBuyMain.shop.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>,
        QuerydslPredicateExecutor<Product>,
        ProductRepositoryCustom {

//    List<Product> findByCategoryAndSubCategory(Long categoryId, Long subCategoryId);
    @Query("SELECT p FROM Product p JOIN p.subCategory sc WHERE sc.category.categoryId = :categoryId AND sc.id = :subCategoryId")
    List<Product> findByCategoryAndSubCategory(@Param("categoryId") Long categoryId, @Param("subCategoryId") Long subCategoryId);

    List<Product> findAllBySeller(Seller seller);

    List<Product> findAll();

    @Query("SELECT p FROM Product p JOIN p.subCategory sc WHERE sc.category.categoryId = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

}