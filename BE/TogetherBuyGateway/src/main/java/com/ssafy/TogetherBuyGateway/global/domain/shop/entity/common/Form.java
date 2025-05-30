package com.ssafy.TogetherBuyGateway.global.domain.shop.entity.common;

import com.ssafy.TogetherBuyGateway.global.domain.community.entity.groupBuyingBoard.GroupBuyingBoard;
import com.ssafy.TogetherBuyGateway.global.domain.shop.entity.order.Order;
import com.ssafy.TogetherBuyGateway.member.entity.Member;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long formId;

    private Long totalAmount; // 상품의 총 개수
    private Long currentAmount; // 현재 구매된 상품 개수

    @ElementCollection(targetClass = DayOfWeek.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "form_days", joinColumns = @JoinColumn(name = "form_id"))
    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING) // 저장시 Enum 이름 저장
    private Set<DayOfWeek> selectedDays;

    private Long startTime;
    private Long endTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_buying_board_id", nullable = false, unique = true)
    private GroupBuyingBoard groupBuyingBoard;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "meeting_location_id")
    private MeetingLocation meetingLocation;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Order order;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "recipient_id")
    private Member recipient;

    // 참여 신청서 (1:N)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "form", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apply> applies;

    public void updateDayOfWeek(Set<DayOfWeek> dayOfWeek) { selectedDays = dayOfWeek; }
    public void updateStartTime(Long startTime) { this.startTime = startTime; }
    public void updateEndTime(Long endTime) { this.endTime = endTime; }
    public void increaseCurrentAmount(Long amount) { this.currentAmount += amount; }
    public void decreaseCurrentAmount(Long amount) { this.currentAmount -= amount; }
    public boolean isReadyForOrder() { return Objects.equals(currentAmount, totalAmount); }
    public void order(Order order) { this.order = order; }
    public void updateRecipient(Member recipient) { this.recipient = recipient; }
}
