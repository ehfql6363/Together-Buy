package com.ssafy.TogetherBuyBilling.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    MEMBER_NOT_FOUND(404, "회원 정보를 찾을 수 없습니다."),
    MEMBER_ALREADY_EXISTS(409, "회원 정보가 이미 존재합니다."),
    TOKEN_NOT_FOUND(401, "토큰이 존재하지 않습니다."),
    INVALID_TOKEN_FORMAT(401, "잘못된 토큰 형식입니다."),
    JWT_TOKEN_EXPIRED(401, "JWT 토큰이 만료되었습니다."),
    JWT_TOKEN_INVALID(401, "유효하지 않은 JWT 토큰입니다."),
    TOKEN_SIGNATURE_INVALID(401, "토큰 서명이 유효하지 않습니다."),
    REFRESH_TOKEN_NOT_FOUND(404, "Refresh 토큰을 찾을 수 없습니다."),
    JWT_TOKEN_GENERATION_FAILED(500, "JWT 토큰 생성에 실패했습니다."),
    REISSUE_ACCESS_TOKEN_AND_CHECK_VALID_ERROR(401, "Access Token 재발급 중 오류가 발생했습니다."),
    REFRESH_TOKEN_EXPIRED(401, "RefreshToken이 만료되었습니다."),
    JWT_TOKEN_REFRESH_FAILED(500, "JWT 토큰 갱신에 실패했습니다."),
    UNKNOWN_PRINCIPAL_TYPE(401, "OAUTH2 TYP2이 다릅니다."),
    UNSUPPORTED_USER_TYPE(401, "제공하지 않는 유저 타입입니다."),
    MEMBER_ID_MISMATCH(401, "토큰과 멤버ID 불일치 합니다"),
    OAUTH2_LOGIN_FAILURE(401, "OAuth2 로그인에 실패했습니다.");

    private final int status;

    private final String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }

}
