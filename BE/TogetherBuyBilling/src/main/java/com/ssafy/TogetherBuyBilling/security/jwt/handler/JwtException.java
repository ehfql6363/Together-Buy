package com.ssafy.TogetherBuyBilling.security.jwt.handler;


import com.ssafy.TogetherBuyBilling.global.exception.BusinessLogicException;
import com.ssafy.TogetherBuyBilling.global.exception.ExceptionCode;

public class JwtException extends BusinessLogicException {

    public JwtException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}