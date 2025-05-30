package com.ssafy.TogetherBuyGateway.security.jwt.service;


import com.ssafy.TogetherBuyGateway.global.exception.ExceptionCode;
import com.ssafy.TogetherBuyGateway.member.entity.Member;
import com.ssafy.TogetherBuyGateway.member.repository.MemberRepository;
import com.ssafy.TogetherBuyGateway.security.jwt.entity.RefreshToken;
import com.ssafy.TogetherBuyGateway.security.jwt.handler.JwtException;
import com.ssafy.TogetherBuyGateway.security.jwt.repository.RefreshTokenRepository;
import java.time.Instant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    // Refresh Token 저장 또는 업데이트
    @Transactional
    public RefreshToken saveOrUpdate(Member member, String token, Long expiry) {
        Optional<RefreshToken> optional = refreshTokenRepository.findByMember(member);

        RefreshToken refreshToken;
        if (optional.isPresent()) {
            refreshToken = optional.get();
            refreshToken.updateRefreshToken(token, expiry);
        } else {
            refreshToken = RefreshToken.builder()
                    .member(member)
                    .provider(member.getProvider())
                    .providerId(member.getProviderId())
                    .refreshToken(token)
                    .expiry(expiry)
                    .build();
        }

        member = memberRepository.saveAndFlush(member);
        refreshToken.registerMember(member);

        RefreshToken savedToken = refreshTokenRepository.save(refreshToken);
        log.info("Saved refresh token: {}", savedToken);


        return savedToken;
    }

    // Refresh Token 조회 (토큰 기준)
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByRefreshToken(token);
    }

    // Refresh Token 만료 여부 확인
    public boolean isExpired(RefreshToken refreshToken) {
        return refreshToken.getExpiry() < Instant.now().toEpochMilli();
    }

    // Refresh Token 삭제 (토큰 기준)
    @Transactional
    public void deleteByToken(RefreshToken token) {
        refreshTokenRepository.delete(token);
    }

    @Transactional
    public void deleteByToken(String token) {
        Optional<RefreshToken> refreshTokenOtp = refreshTokenRepository.findByRefreshToken(token);
        if (refreshTokenOtp.isEmpty()) {
            throw new JwtException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND);
        }
        RefreshToken refreshToken = refreshTokenOtp.get();
        deleteByToken(refreshToken);
    }
}
