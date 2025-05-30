package com.ssafy.TogetherBuyMain.security.jwt.handler;

import com.ssafy.TogetherBuyMain.global.exception.BusinessLogicException;
import com.ssafy.TogetherBuyMain.global.exception.ExceptionCode;

public class JwtException extends BusinessLogicException {

    public JwtException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}