package com.ssafy.TogetherBuyNotification.global.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private final Key accessKey;
    private final Key refreshKey;
    private final long accessTokenExpireTime;
    private final long refreshTokenExpireTime;

    public JwtUtil(
            @Value("${spring.jwt.access-token-secret}") String accessSecret,
            @Value("${spring.jwt.access-token-expiration}") long accessTokenExpireTime,
            @Value("${spring.jwt.refresh-token-secret}") String refreshSecret,
            @Value("${spring.jwt.refresh-token-expiration}") long refreshTokenExpireTime
    ) {
        this.accessKey = Keys.hmacShaKeyFor(accessSecret.getBytes());
        this.refreshKey = Keys.hmacShaKeyFor(refreshSecret.getBytes());
        this.accessTokenExpireTime = accessTokenExpireTime;
        this.refreshTokenExpireTime = refreshTokenExpireTime;
    }

    /**
     * Access Token 생성
     */
    public String generateAccessToken(String email) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + accessTokenExpireTime);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Refresh Token 생성
     */
    public String generateRefreshToken(String email) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + refreshTokenExpireTime);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(refreshKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        return validateToken(token, accessKey);
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, refreshKey);
    }

    public String getEmailFromAccessToken(String token) {
        return parseClaims(token, accessKey).getSubject();
    }

    public String getEmailFromRefreshToken(String token) {
        return parseClaims(token, refreshKey).getSubject();
    }

    /**
     * Refresh 토큰 만료시간(ms) 반환
     */
    public long getRefreshTokenExpireTimeMillis() {
        return refreshTokenExpireTime;
    }

    private boolean validateToken(String token, Key key) {
        try {
            parseClaims(token, key);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (JwtException e) {
            return false;
        }
    }

    private Claims parseClaims(String token, Key key) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}