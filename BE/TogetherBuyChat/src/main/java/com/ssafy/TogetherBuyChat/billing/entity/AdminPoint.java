package com.ssafy.TogetherBuyChat.billing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    private Long balance;

    @OneToMany(mappedBy = "adminPoint", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PointChargeHistory> pointChargeHistories;

    public void charge(Long cost) {
        this.balance = this.balance + cost;
    }
    public void refund(Long cost) {
        this.balance = this.balance - cost;
    }
}
