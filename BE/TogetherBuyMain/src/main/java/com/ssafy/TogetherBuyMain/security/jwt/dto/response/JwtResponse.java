package com.ssafy.TogetherBuyMain.security.jwt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // 생성자
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
}
