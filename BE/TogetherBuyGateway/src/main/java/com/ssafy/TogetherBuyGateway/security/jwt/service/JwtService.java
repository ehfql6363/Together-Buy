package com.ssafy.TogetherBuyGateway.security.jwt.service;

import com.ssafy.TogetherBuyGateway.global.exception.BusinessLogicException;
import com.ssafy.TogetherBuyGateway.global.exception.ExceptionCode;
import com.ssafy.TogetherBuyGateway.member.entity.Member;
import com.ssafy.TogetherBuyGateway.security.jwt.dto.response.JwtResponse;
import com.ssafy.TogetherBuyGateway.security.jwt.entity.RefreshToken;
import com.ssafy.TogetherBuyGateway.security.jwt.handler.JwtException;
import com.ssafy.TogetherBuyGateway.security.jwt.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    // Access Token과 Refresh Token 발급
    public JwtResponse generateToken(Member member){  // 여기 수정
        try {
            // Access Token 생성
            String accessToken = jwtUtil.generateAccessToken(member);

            // Refresh Token 생성
            String refresh = jwtUtil.generateRefreshToken(member);

            // Refresh Token의 만료 시간 설정 (환경 변수 사용)
            long refreshTokenExpiry = Instant.now().plusSeconds(jwtUtil.getRefreshTokenExpiration()).toEpochMilli();

            // Refresh Token 저장 또는 업데이트
            RefreshToken refreshToken = refreshTokenService.saveOrUpdate(member, refresh, refreshTokenExpiry);

            return new JwtResponse(accessToken, refreshToken.getRefreshToken());

        } catch (Exception e) {
            throw new JwtException(ExceptionCode.JWT_TOKEN_GENERATION_FAILED);
        }
    }

    @Transactional
    // Refresh Token으로 Access Token, Refresh토큰 재발급
    public JwtResponse refreshAccessRefreshToken(String authorizationHeader) {
        try {

            String token = extractToken(authorizationHeader);

            // Refresh Token 조회
            RefreshToken refreshToken = refreshTokenService.findByToken(token)
                    .orElseThrow(() -> new JwtException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND));

            // Refresh Token 만료 여부 확인
            if (refreshTokenService.isExpired(refreshToken)) {
                // 만료된 경우 DB에서 삭제
                refreshTokenService.deleteByToken(refreshToken);
                throw new JwtException(ExceptionCode.REFRESH_TOKEN_EXPIRED);
            }
            //db존재하는 사용자 validta 만료

            Member member = refreshToken.getMember();
            // 새로운 access token과 refresh token 생성
            return generateToken(member);

        } catch (Exception e) {
            throw new JwtException(ExceptionCode.JWT_TOKEN_REFRESH_FAILED);
        }
    }

    @Transactional
    // AccessToken만 재발급
    public void reissueAccessTokenAndCheckValid(String authorizationHeader, HttpServletResponse response){
        try {
            String token = extractToken(authorizationHeader);

            // Refresh Token 검증
            // Refresh Token 존재하나 확인
            RefreshToken refreshToken = refreshTokenService.findByToken(token)
                    .orElseThrow(() -> new JwtException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND));

            // refresh 유효성 검증
            if (!jwtUtil.validateRefreshToken(token)) {
                throw new JwtException(ExceptionCode.JWT_TOKEN_INVALID);
            }

            // Refresh Token 만료 여부 확인
            if (refreshTokenService.isExpired(refreshToken)) {
                throw new JwtException(ExceptionCode.REFRESH_TOKEN_EXPIRED);
            }

            Member member = refreshToken.getMember();

            // 새로운 Access Token만 생성
            String newAccessToken = jwtUtil.generateAccessToken(member);

            // 쿠키에 담아주기
            jwtUtil.addCookie(response, "Authorization",newAccessToken, 3600);  // 예: 1시간 동안 유효
            jwtUtil.addCookie(response, "Refresh-Token", token, 86400);  // 예: 24시간 동안 유효
        } catch (Exception e) {
            throw new JwtException(ExceptionCode.REISSUE_ACCESS_TOKEN_AND_CHECK_VALID_ERROR);
        }
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new BusinessLogicException(ExceptionCode.INVALID_TOKEN_FORMAT);
        }
        return authorizationHeader.substring(7);
    }

    public String generateAccessToken(Member member) {
        return jwtUtil.generateAccessToken(member);
    }

    public String generateRefreshToken(Member member){
        return jwtUtil.generateRefreshToken(member);
    }

    // JWT 토큰의 유효성 검증 util에서 로직 처리함
    public boolean validateToken(String token){
        return jwtUtil.validateAccessToken(token);
    }

    // JWT 토큰에서 providerId 추출
    public String getUserIdFromAccessToken(String token){
        return jwtUtil.getProviderIdFromAccessToken(token);
    }

    @Transactional
    public void logout(String authorizationHeader, HttpServletResponse response) {

        String token = extractToken(authorizationHeader);

        RefreshToken refreshToken = refreshTokenService.findByToken(token)
                .orElseThrow(() -> new JwtException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND));

        // 2. DB에서 Refresh 토큰 삭제
        refreshTokenService.deleteByToken(refreshToken);

        // 3. 응답에 쿠키 삭제
        jwtUtil.deleteRefreshTokenCookie(response);
    }
}
