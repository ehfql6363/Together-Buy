package com.ssafy.TogetherBuyMain.community.repository.freeBoard;

import com.ssafy.TogetherBuyMain.community.dto.freeboard.HotFreeBoardDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface FreeBoardRepositoryCustom {
    long updateFreeBoard(Long id, String title, String content, LocalDateTime updatedAt);
    long deleteFreeBoard(Long id);
    List<HotFreeBoardDTO> findHotFreeBoard();
}
