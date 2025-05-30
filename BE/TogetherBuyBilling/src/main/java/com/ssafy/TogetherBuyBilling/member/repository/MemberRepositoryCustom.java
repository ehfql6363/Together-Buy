package com.ssafy.TogetherBuyBilling.member.repository;

import com.ssafy.TogetherBuyBilling.member.entity.Member;

public interface MemberRepositoryCustom {
    Member findByEmail(String email);
}
