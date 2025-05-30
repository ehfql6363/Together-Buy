package com.ssafy.TogetherBuyChat.security.jwt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequestDto {
    private String email;
    private String refreshToken;
}
