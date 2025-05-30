package com.ssafy.TogetherBuyMain.security.oauth.util;

import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.security.jwt.dto.response.JwtResponse;
import com.ssafy.TogetherBuyMain.security.jwt.entity.RefreshToken;
import com.ssafy.TogetherBuyMain.security.jwt.repository.RefreshTokenRepository;
import com.ssafy.TogetherBuyMain.security.jwt.service.JwtService;
import com.ssafy.TogetherBuyMain.security.jwt.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2Util {

    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public boolean isRefreshTokenValid(Member member) {
        if(member.getRefreshToken() == null) {return false;}
        Optional<RefreshToken> refreshTokenOtp = refreshTokenRepository.findByRefreshToken(member.getRefreshToken().getRefreshToken());
        if (refreshTokenOtp.isEmpty()) {
            return false;
        }
        RefreshToken refreshToken = refreshTokenOtp.get();

        return jwtUtil.validateRefreshToken(refreshToken.getRefreshToken());
    }

    public String[] getTokens(Member member) {
        return generateAndSaveNewTokens(member);
    }

    public String getAccessToken(Member member) {
        return jwtService.generateAccessToken(member);
    }

    public String[] handleNewMember(Member member, HttpServletResponse response) {
        // 신규 회원은 항상 Access Token과 Refresh Token을 새로 발급
        return generateAndSaveNewTokens(member);
    }

    private String[] generateAndSaveNewTokens(Member member) {
        JwtResponse jwtResponse = jwtService.generateToken(member);
        return new String[]{jwtResponse.getAccessToken(), jwtResponse.getRefreshToken()};
    }

}
