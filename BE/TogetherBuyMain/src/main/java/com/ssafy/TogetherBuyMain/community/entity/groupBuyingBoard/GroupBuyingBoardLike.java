package com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard;

import com.ssafy.TogetherBuyMain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupBuyingBoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupBuyingLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_buying_board_id")
    private GroupBuyingBoard groupBuyingBoard;
}
