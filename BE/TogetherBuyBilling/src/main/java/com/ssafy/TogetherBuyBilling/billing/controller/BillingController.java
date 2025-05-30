package com.ssafy.TogetherBuyBilling.billing.controller;


import com.ssafy.TogetherBuyBilling.billing.dto.BillingKeyRegisterRequestDTO;
import com.ssafy.TogetherBuyBilling.billing.dto.HasBillingKeyResponseDTO;
import com.ssafy.TogetherBuyBilling.billing.dto.PointListResponseDTO;
import com.ssafy.TogetherBuyBilling.billing.dto.RegisterBillingKeyResponseDTO;
import com.ssafy.TogetherBuyBilling.billing.service.BillingService;
import com.ssafy.TogetherBuyBilling.billing.service.PortOneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;
    private final PortOneService portOneService;

    @GetMapping("/ping")
    public ResponseEntity<String> pong() {
        return ResponseEntity.ok("pong");
    }

    @GetMapping("/points")
    public ResponseEntity<PointListResponseDTO> getPointList(
            @RequestHeader(name = "Authorization", required = false) String authorization
    ) {
        PointListResponseDTO response = billingService.getPointList(authorization);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pay/point/{pointId}")
    public ResponseEntity<?> payPoint(
            @RequestHeader(name = "Authorization", required = false) String authorization,
            @PathVariable(name = "pointId") Long pointId
    ) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/key")
    public ResponseEntity<?> checkBillingKey(
            @RequestHeader(name = "Authorization", required = false) String authorization
    ) {
        HasBillingKeyResponseDTO response = billingService.hasBillingKey(authorization);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/key")
    public ResponseEntity<?> registerBillingKey(
            @RequestHeader(name = "Authorization", required = false) String authorization,
            @RequestBody BillingKeyRegisterRequestDTO billingKey
    ) {
        RegisterBillingKeyResponseDTO response = billingService.registerBillingKey(authorization, billingKey);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/webhook")
    public ResponseEntity<?> handleWebhook(
            @RequestBody Map<String, Object> payload
    ) {
        String type = (String) payload.get("type");
        String result = "";
        System.out.println(payload);
        if (type.equals("Transaction.paid")) {
            result = portOneService.getPaidHistory((Map<String, Object>) payload.get("data"));
        }
        return ResponseEntity.ok(result);
    }
}
