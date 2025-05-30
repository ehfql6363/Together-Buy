package com.ssafy.TogetherBuyNotification.shop.entity.common;

import com.ssafy.TogetherBuyNotification.community.entity.groupBuyingBoard.GroupBuyingBoard;
import com.ssafy.TogetherBuyNotification.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private LocalDateTime appliedAt;

    private Long amount; // 구매(참여) 수량
    private Long cost; // 구매(참여) 금액


}
