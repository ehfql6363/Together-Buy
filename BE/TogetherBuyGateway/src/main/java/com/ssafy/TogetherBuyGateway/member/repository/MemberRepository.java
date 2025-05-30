package com.ssafy.TogetherBuyGateway.member.repository;


import com.ssafy.TogetherBuyGateway.member.entity.Member;
import com.ssafy.TogetherBuyGateway.member.entity.Provider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
    Optional<Member> findByProviderId(String providerId);
    // provider와 providerId로 함께 찾는 메서드 추가
    Optional<Member> findByProviderAndProviderId(Provider provider, String providerId);

    Optional<Member> findByEmail(String email);
}
