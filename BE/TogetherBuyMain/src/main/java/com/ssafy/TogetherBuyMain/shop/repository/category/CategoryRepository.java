package com.ssafy.TogetherBuyMain.shop.repository.category;

import com.ssafy.TogetherBuyMain.shop.entity.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
