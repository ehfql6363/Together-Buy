package com.ssafy.TogetherBuyGateway.global.domain.shop.entity.common;


import com.ssafy.TogetherBuyGateway.global.domain.community.entity.groupBuyingBoard.GroupBuyingBoard;
import com.ssafy.TogetherBuyGateway.member.entity.Member;
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
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.json.JsonWriter.Members;

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
