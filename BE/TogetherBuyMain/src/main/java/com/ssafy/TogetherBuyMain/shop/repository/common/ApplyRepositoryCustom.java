package com.ssafy.TogetherBuyMain.shop.repository.common;

import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.shop.entity.common.Apply;

import java.util.List;

public interface ApplyRepositoryCustom {
    List<Apply> findAllByMember(Member member);
}
