package com.ssafy.TogetherBuyBilling.billing.service;

import com.ssafy.TogetherBuyBilling.billing.dto.*;
import com.ssafy.TogetherBuyBilling.billing.entity.PaymentPoint;
import com.ssafy.TogetherBuyBilling.billing.repository.PaymentPointRepository;
import com.ssafy.TogetherBuyBilling.global.util.MemberUtil;
import com.ssafy.TogetherBuyBilling.member.entity.Member;
import com.ssafy.TogetherBuyBilling.member.entity.MemberPoint;
import com.ssafy.TogetherBuyBilling.member.repository.MemberPointRepository;
import com.ssafy.TogetherBuyBilling.security.jwt.service.JwtService;
import com.ssafy.TogetherBuyBilling.security.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillingService {

    private final JwtUtil jwtUtil;
    private final JwtService jwtService;
    private final MemberUtil memberUtil;

    private final PaymentPointRepository paymentPointRepository;
    private final MemberPointRepository memberPointRepository;

    @Transactional(readOnly = true)
    public PointListResponseDTO getPointList(String authorization) {
        List<PaymentPoint> pointList = paymentPointRepository.findAll();
        List<PointResponseDTO> pointResponseList = getPoints(pointList);

        return new PointListResponseDTO(pointResponseList);
    }

    // 빌링키 유무 확인 로직
    @Transactional(readOnly = true)
    public HasBillingKeyResponseDTO hasBillingKey(String authorization) {
        String accessToken = jwtService.extractToken(authorization);
        Member member = memberUtil.getMember(accessToken);
        MemberPoint memberPoint = member.getMemberPoint();

        return new HasBillingKeyResponseDTO(memberPoint.getBillingKey() == null ? "empty" : memberPoint.getBillingKey());
    }

    @Transactional
    public RegisterBillingKeyResponseDTO registerBillingKey(
            String authorization,
            BillingKeyRegisterRequestDTO request) {
        String accessToken = jwtService.extractToken(authorization);
        Member member = memberUtil.getMember(accessToken);
        MemberPoint memberPoint = member.getMemberPoint();

        if(memberPoint.getBillingKey() != null || request.billingKey().isBlank()) {
            return new RegisterBillingKeyResponseDTO("404", "not found billing key");
        }

        memberPoint.registerBillingKey(request.billingKey());
        memberPointRepository.save(memberPoint);

        return new RegisterBillingKeyResponseDTO("200", "success register billing key");
    }

    private List<PointResponseDTO> getPoints(List<PaymentPoint> pointList) {
        return pointList.stream().map(PointResponseDTO::of).toList();
    }
}
