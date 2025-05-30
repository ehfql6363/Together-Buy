package com.ssafy.TogetherBuyChat.community.repository;

import com.ssafy.TogetherBuyChat.community.entity.groupBuyingBoard.GroupBuyingBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GroupBuyingBoardRepository extends JpaRepository<GroupBuyingBoard, Long> {

    // groupBuyingBoardId를 이용해 특정 그룹 구매 게시판 정보 가져오기
    GroupBuyingBoard findByGroupBuyingBoardId(Long groupBuyingBoardId);

    // groupBuyingBoardId를 통해 관련된 ChatRoom 객체 가져오기
    @Query("SELECT g.chatRoom FROM GroupBuyingBoard g WHERE g.groupBuyingBoardId = :groupBuyingBoardId")
    Optional<Object> findChatRoomByGroupBuyingBoardId(@Param("groupBuyingBoardId") Long groupBuyingBoardId);

}
