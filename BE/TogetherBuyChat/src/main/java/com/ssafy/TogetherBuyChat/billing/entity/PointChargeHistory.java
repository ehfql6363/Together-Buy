package com.ssafy.TogetherBuyChat.billing.entity;

import com.ssafy.TogetherBuyChat.member.entity.MemberPoint;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointChargeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 충전 금액
    private Long chargeAmount;

    // 충전 시각
    private LocalDateTime chargeDateTime;

    // 기타 필요한 필드 (예: 충전 수단, 메모 등)
    // private String paymentMethod;
    // private String memo;
    private ChargeStatus chargeStatus;

    // 다대일(ManyToOne) 관계 설정
    // 어떤 Point(=어떤 Member)에서 충전 내역이 발생했는지 알 수 있음.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_point_id")
    private MemberPoint memberPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_point_id")
    private AdminPoint adminPoint;

    public void updateMemberPoint(MemberPoint memberPoint) {
        this.memberPoint = memberPoint;
    }
}
