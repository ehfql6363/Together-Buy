package com.ssafy.TogetherBuyChat.security.jwt.handler;

import com.ssafy.TogetherBuyChat.global.exception.BusinessLogicException;
import com.ssafy.TogetherBuyChat.global.exception.ExceptionCode;

public class JwtException extends BusinessLogicException {

    public JwtException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}