package com.ssafy.TogetherBuyBilling.member.repository;

import com.ssafy.TogetherBuyBilling.member.entity.Member;
import com.ssafy.TogetherBuyBilling.member.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    boolean existsByEmail(String email);
    Optional<Member> findByProviderId(String providerId);
    // provider와 providerId로 함께 찾는 메서드 추가
    Optional<Member> findByProviderAndProviderId(Provider provider, String providerId);
}
