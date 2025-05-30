package com.ssafy.TogetherBuyBilling.community.entity.freeBoard;

import com.ssafy.TogetherBuyBilling.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreeBoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long freeBoardLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_board_id")
    private FreeBoard freeBoard;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    public static FreeBoardLike createFreeBoardLike(Member member, FreeBoard freeBoard) {
        return FreeBoardLike.builder()
                .member(member)
                .freeBoard(freeBoard)
                .createdAt(LocalDateTime.now())
                .build();
    }

}
