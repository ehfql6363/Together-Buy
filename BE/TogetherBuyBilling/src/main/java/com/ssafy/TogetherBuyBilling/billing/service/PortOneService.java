package com.ssafy.TogetherBuyBilling.billing.service;

import com.ssafy.TogetherBuyBilling.billing.entity.AdminPoint;
import com.ssafy.TogetherBuyBilling.billing.entity.ChargeStatus;
import com.ssafy.TogetherBuyBilling.billing.entity.PointChargeHistory;
import com.ssafy.TogetherBuyBilling.billing.repository.AdminPointRepository;
import com.ssafy.TogetherBuyBilling.member.entity.Member;
import com.ssafy.TogetherBuyBilling.member.entity.MemberPoint;
import com.ssafy.TogetherBuyBilling.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PortOneService {

    private final MemberRepository memberRepository;
    private final AdminPointRepository adminPointRepository;

    public String getPaidHistory(Map<String, Object> params) {
        for(String key : params.keySet()) {
            System.out.println(key + " : " + params.get(key));
        }

        String paymentId = (String) params.get("paymentId");
        String[] paymentData = paymentId.split("-");
        String memberId = paymentData[0];
        Long chargeAmount = Long.valueOf(paymentData[1]);
        Long cost = Long.valueOf(paymentData[2]);

        Member member = memberRepository.findById(Long.parseLong(memberId))
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        MemberPoint memberPoint = member.getMemberPoint();

        AdminPoint adminPoint = adminPointRepository.findAdminPoint();

        PointChargeHistory history = PointChargeHistory.builder()
                .chargeAmount(chargeAmount)
                .memberPoint(memberPoint)
                .chargeStatus(ChargeStatus.COMPLETE)
                .chargeDateTime(LocalDateTime.now())
                .adminPoint(adminPoint)
                .build();

        memberPoint.addChargeHistory(history);
        memberPoint.charge(chargeAmount);
        adminPoint.charge(cost);
        memberRepository.save(member);

        return "Done";
    }
}
