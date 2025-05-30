package com.ssafy.TogetherBuyBilling.security.jwt.util;

import com.ssafy.TogetherBuyBilling.global.exception.BusinessLogicException;
import com.ssafy.TogetherBuyBilling.global.exception.ExceptionCode;
import com.ssafy.TogetherBuyBilling.member.entity.Member;
import com.ssafy.TogetherBuyBilling.security.jwt.handler.JwtException;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${spring.jwt.access-token-secret}")
    private String accessTokenSecret;

    @Value("${spring.jwt.refresh-token-secret}")
    private String refreshTokenSecret;

    @Value("${spring.jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${spring.jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    @PostConstruct
    protected void init() {
        // 환경 변수에 저장된 단순 문자를 Base64 인코딩
        accessTokenSecret = Base64.getEncoder().encodeToString(accessTokenSecret.getBytes());
        refreshTokenSecret = Base64.getEncoder().encodeToString(refreshTokenSecret.getBytes());
    }

    // Access Token 생성
    public String generateAccessToken(Member member){
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", member.getMemberId());
        claims.put("nickname", member.getNickname() == null ? "" : member.getNickname());
        claims.put("profileImage", member.getProfileImage() == null ? "" : member.getProfileImage().getUrl());
        claims.put("email", member.getEmail());
        claims.put("provider", member.getProvider());

        Key accessKey = new SecretKeySpec(accessTokenSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(member.getProviderId()) // providerId 기준으로 진행
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(accessKey)
                .compact();
    }

    // Refresh Token 생성
    public String generateRefreshToken(Member member){
        Key refreshKey = new SecretKeySpec(refreshTokenSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject(member.getProviderId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ refreshTokenExpiration))
                .signWith(refreshKey)
                .compact();
    }

    // JWT 헤더,페이로드,서명으로 파싱 후 클레임 추출(jwt 정보추출)
    public String getProviderIdFromAccessToken(String token) {
        try {
            Key accessKey = new SecretKeySpec(accessTokenSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());

            return Jwts.parserBuilder()
                    .setSigningKey(accessKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            throw new JwtException(ExceptionCode.JWT_TOKEN_EXPIRED);
        } catch (JwtException e) {
            // 그 외 JWT 관련 예외 (유효하지 않은 토큰 등)
            throw new JwtException(ExceptionCode.JWT_TOKEN_INVALID);
        }
    }

    // JWT 헤더,페이로드,서명으로 파싱 후 클레임 추출(jwt 정보추출)
    public String getProviderIdFromRefreshToken(String token) {
        try {
            Key refreshKey = new SecretKeySpec(refreshTokenSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());

            return Jwts.parserBuilder()
                    .setSigningKey(refreshKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            throw new JwtException(ExceptionCode.JWT_TOKEN_EXPIRED);
        } catch (JwtException e) {
            // 그 외 JWT 관련 예외 (유효하지 않은 토큰 등)
            throw new JwtException(ExceptionCode.JWT_TOKEN_INVALID);
        }
    }

    // JWT 헤더,페이로드,서명으로 파싱 후 클레임 추출(jwt 정보추출)
    public String getProviderFromAccessToken(String token) {
        try {
            Key accessKey = new SecretKeySpec(accessTokenSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());

            return Jwts.parserBuilder()
                    .setSigningKey(accessKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("provider", String.class);
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            throw new JwtException(ExceptionCode.JWT_TOKEN_EXPIRED);
        } catch (JwtException e) {
            // 그 외 JWT 관련 예외 (유효하지 않은 토큰 등)
            throw new JwtException(ExceptionCode.JWT_TOKEN_INVALID);
        }
    }

    // JWT 헤더,페이로드,서명으로 파싱 후 클레임 추출(jwt 정보추출)
    public Long getMemberIdFromToken(String token) {
        try {
            Key accessKey = new SecretKeySpec(accessTokenSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());

            return Jwts.parserBuilder()
                    .setSigningKey(accessKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("memberId", Long.class);
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            throw new JwtException(ExceptionCode.JWT_TOKEN_EXPIRED);
        } catch (JwtException e) {
            // 그 외 JWT 관련 예외 (유효하지 않은 토큰 등)
            throw new JwtException(ExceptionCode.JWT_TOKEN_INVALID);
        }
    }

    // JWT 헤더,페이로드,서명으로 파싱 후 클레임 추출(jwt 정보추출)
    public String getNicknameFromToken(String token) {
        try {
            Key accessKey = new SecretKeySpec(accessTokenSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());

            return Jwts.parserBuilder()
                    .setSigningKey(accessKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("nickname", String.class);
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            throw new JwtException(ExceptionCode.JWT_TOKEN_EXPIRED);
        } catch (JwtException e) {
            // 그 외 JWT 관련 예외 (유효하지 않은 토큰 등)
            throw new JwtException(ExceptionCode.JWT_TOKEN_INVALID);
        }
    }

    // JWT 헤더,페이로드,서명으로 파싱 후 클레임 추출(jwt 정보추출)
    public String getProfileImageFromToken(String token) {
        try {
            Key accessKey = new SecretKeySpec(accessTokenSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());

            return Jwts.parserBuilder()
                    .setSigningKey(accessKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("profileImage", String.class);
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            throw new JwtException(ExceptionCode.JWT_TOKEN_EXPIRED);
        } catch (JwtException e) {
            // 그 외 JWT 관련 예외 (유효하지 않은 토큰 등)
            throw new JwtException(ExceptionCode.JWT_TOKEN_INVALID);
        }
    }

    // 토큰 유효성 검증
    public boolean validateAccessToken(String token) {
        try {
            Key accessKey = new SecretKeySpec(accessTokenSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());

            Jwts.parserBuilder()
                    .setSigningKey(accessKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new JwtException(ExceptionCode.JWT_TOKEN_EXPIRED);
        } catch (SecurityException | SignatureException e) {
            throw new JwtException(ExceptionCode.TOKEN_SIGNATURE_INVALID);
        } catch (MalformedJwtException e) {
            throw new JwtException(ExceptionCode.JWT_TOKEN_INVALID);
        } catch (JwtException e) {
            throw new JwtException(ExceptionCode.JWT_TOKEN_INVALID);
        }
    }

    // 토큰 유효성 검증
    public boolean validateRefreshToken(String token) {
        try {
            Key refreshKey = new SecretKeySpec(refreshTokenSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
            Jwts.parserBuilder()
                    .setSigningKey(refreshKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new JwtException(ExceptionCode.JWT_TOKEN_EXPIRED);
        } catch (SecurityException | SignatureException e) {
            throw new JwtException(ExceptionCode.TOKEN_SIGNATURE_INVALID);
        } catch (MalformedJwtException e) {
            throw new JwtException(ExceptionCode.JWT_TOKEN_INVALID);
        } catch (JwtException e) {
            throw new JwtException(ExceptionCode.JWT_TOKEN_INVALID);
        }
    }

    // 쿠키에서 Refresh 토큰 추출
    public String extractRefreshTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // 쿠키 삭제
    public void deleteRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // HTTPS 환경에서만 사용
        cookie.setPath("/");
        cookie.setMaxAge(0); // 쿠키 만료 시간을 0으로 설정하여 삭제
        response.addCookie(cookie);
    }

    // Refresh 토큰 만료시간 반환
    public long getRefreshTokenExpireTimeMillis() {
        return refreshTokenExpiration;
    }

    //  HTTP 요청에서 Access Token을 추출하고 해당 토큰에서 이메일을 추출하는 메서드
    public String extractEmailFromRequest(HttpServletRequest request) {

        // Authorization 헤더에서 토큰 추출
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken == null) {
            throw new JwtException(ExceptionCode.TOKEN_NOT_FOUND);
        }

        // Bearer 토큰 형식 검증
        if (!bearerToken.startsWith("Bearer ")) {
            throw new JwtException(ExceptionCode.INVALID_TOKEN_FORMAT);
        }

        // "Bearer " 부분을 제외한 실제 토큰 값 추출
        String token = bearerToken.substring(7);

        // 토큰 유효성 검증 후 이메일 추출
        validateAccessToken(token);
        return getProviderIdFromRefreshToken(token);
    }

    public void addTokenInCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(false);
//        cookie.setSecure(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public void addAuthHeaderAccessToken(HttpServletResponse response, String token) {
        response.setHeader("Authorization", "Bearer " + token);
    }

    // Header에서 토큰 추출
    public String extractToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new BusinessLogicException(ExceptionCode.INVALID_TOKEN_FORMAT);
        }
        return authorizationHeader.substring(7);
    }
}
