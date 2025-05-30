package com.ssafy.TogetherBuyNotification.member.entity;

import com.ssafy.TogetherBuyNotification.billing.entity.PointChargeHistory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "points")
@NoArgsConstructor
@AllArgsConstructor
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    private Long balance;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 추가: PointChargeHistory와 일대다(OneToMany) 매핑
    @OneToMany(mappedBy = "point", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PointChargeHistory> chargeHistories = new ArrayList<>();

    // Getter, Setter...

    // 편의 메서드 예시
    public void addChargeHistory(PointChargeHistory history) {
//        this.chargeHistories.add(history);
//        history.setPoint(this);
    }

    // 포인트 충전 로직 예시
    public void charge(Long amount) {
//        this.balance += amount;
//        PointChargeHistory history = new PointChargeHistory();
//        history.setChargeAmount(amount);
//        history.setChargeDateTime(java.time.LocalDateTime.now());
//        this.addChargeHistory(history);
    }

}