package com.ssafy.TogetherBuyNotification.billing.entity;

import com.ssafy.TogetherBuyNotification.member.entity.Point;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "point_charge_history")
@NoArgsConstructor
@AllArgsConstructor
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
    private ChargeStatus chargeStatus; //

    // 다대일(ManyToOne) 관계 설정
    // 어떤 Point(=어떤 Member)에서 충전 내역이 발생했는지 알 수 있음.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    private Point point;

    // Getter, Setter...
}
