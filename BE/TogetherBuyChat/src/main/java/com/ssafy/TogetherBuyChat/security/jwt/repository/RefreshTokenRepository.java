package com.ssafy.TogetherBuyChat.security.jwt.repository;

import com.ssafy.TogetherBuyChat.member.entity.Member;
import com.ssafy.TogetherBuyChat.security.jwt.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String token);
    Optional<RefreshToken> findByMember(Member member);
}