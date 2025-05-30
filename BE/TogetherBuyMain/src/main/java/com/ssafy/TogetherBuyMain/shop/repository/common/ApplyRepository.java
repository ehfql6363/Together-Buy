package com.ssafy.TogetherBuyMain.shop.repository.common;

import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.shop.entity.common.Apply;
import com.ssafy.TogetherBuyMain.shop.entity.common.Form;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplyRepository extends JpaRepository<Apply, Long>, ApplyRepositoryCustom {
    Optional<Apply> findByFormAndMember(Form form, Member member);
}
