package com.ssafy.TogetherBuyMain.member.entity;

import com.ssafy.TogetherBuyMain.billing.entity.PointChargeHistory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberPointId;

    private Long balance;

    private String billingKey;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 추가: PointChargeHistory와 일대다(OneToMany) 매핑
    @OneToMany(mappedBy = "memberPoint", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PointChargeHistory> chargeHistories = new ArrayList<>();

    // Getter, Setter...

    // 편의 메서드 예시
    public void addChargeHistory(PointChargeHistory history) {
        this.chargeHistories.add(history);
        history.updateMemberPoint(this);
    }

    // 포인트 충전 로직 예시
    public void charge(Long amount) {
        this.balance += amount;
    }

    public void registerBillingKey(String billingKey) {
        this.billingKey = billingKey;
    }

}