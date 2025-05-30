package com.ssafy.TogetherBuyMain.community.repository.groupBuyingBoard;

import com.ssafy.TogetherBuyMain.community.dto.groupBuyingBoard.ExpiringGroupBuyingBoardDTO;
import com.ssafy.TogetherBuyMain.member.entity.Member;

import java.util.List;

public interface GroupBuyingBoardRepositoryCustom {
    List<ExpiringGroupBuyingBoardDTO> findExpiringGroupBuyingBoardByMemberLocation(Member member);
}
