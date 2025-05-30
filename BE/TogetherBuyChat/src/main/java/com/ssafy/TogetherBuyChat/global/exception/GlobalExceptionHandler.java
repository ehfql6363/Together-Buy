package com.ssafy.TogetherBuyChat.global.exception;

import com.ssafy.TogetherBuyChat.shop.exception.ProductException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<Map<String, Object>> handleProductException(ProductException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("msg", e.getErrorCode().getMessage());
        response.put("code", e.getErrorCode().getCode());  // 비즈니스 코드 (P001, P002 등)
        return ResponseEntity.status(e.getErrorCode().getStatusCode()).body(response);
    }

    // 다른 예외 핸들러 추가 가능!
}