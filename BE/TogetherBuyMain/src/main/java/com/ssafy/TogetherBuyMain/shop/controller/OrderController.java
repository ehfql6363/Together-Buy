package com.ssafy.TogetherBuyMain.shop.controller;

import com.ssafy.TogetherBuyMain.shop.dto.BooleanResponseDTO;
import com.ssafy.TogetherBuyMain.shop.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final FormService formService;

    @PostMapping("/{formId}/order")
    public ResponseEntity<?> startOrder(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("formId") Long formId) {
        BooleanResponseDTO response = formService.startOrder(authorizationHeader, formId);
        return ResponseEntity.ok(response);
    }
}
