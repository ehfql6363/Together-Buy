package com.ssafy.TogetherBuyGateway.security.jwt.handler;


import com.ssafy.TogetherBuyGateway.global.exception.BusinessLogicException;
import com.ssafy.TogetherBuyGateway.global.exception.ExceptionCode;

public class JwtException extends BusinessLogicException {

    public JwtException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}