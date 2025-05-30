package com.ssafy.TogetherBuyBilling.shop.entity.common;

import com.ssafy.TogetherBuyBilling.community.entity.groupBuyingBoard.GroupBuyingBoard;
import com.ssafy.TogetherBuyBilling.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id", nullable = false)
    private Form form;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_buying_board_id", nullable = false)
    private GroupBuyingBoard groupBuyingBoard;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplyStatus status;

    @Column(nullable = false)
    private LocalDate appliedAt;

    private Long amount; // 구매(참여) 수량
    private Long cost; // 구매(참여) 금액


}
