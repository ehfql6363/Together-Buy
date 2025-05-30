package com.ssafy.TogetherBuyChat.security.jwt.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JwtExceptionHandler {

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handleJwtException(JwtException e) {
        return ResponseEntity.status(e.getExceptionCode().getStatus()).body(e.getExceptionCode().getMessage());
    }
}