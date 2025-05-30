package com.ssafy.TogetherBuyMain.shop.repository.product;

import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.shop.dto.product.WishedProductDTO;

import java.util.List;

public interface ProductRepositoryCustom {
    List<WishedProductDTO> findAllByWishMember(Member member);
}
