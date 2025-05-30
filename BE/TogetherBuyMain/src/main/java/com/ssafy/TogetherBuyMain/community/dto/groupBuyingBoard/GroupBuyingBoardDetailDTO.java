package com.ssafy.TogetherBuyMain.community.dto.groupBuyingBoard;

import com.ssafy.TogetherBuyMain.chatting.entity.ChatRoom;
import com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoard;
import com.ssafy.TogetherBuyMain.shop.entity.common.DayOfWeek;
import com.ssafy.TogetherBuyMain.shop.entity.common.Form;
import com.ssafy.TogetherBuyMain.shop.entity.common.MeetingLocation;

import java.time.LocalDateTime;
import java.util.Set;

public record GroupBuyingBoardDetailDTO(
        Long groupBuyingBoardId,
        String groupBuyingBoardTitle,
        String groupBuyingBoardContent,
        String groupBuyingBoardImage,
        Long likes,
        Long views,
        String meetingLocation,
        Long meetingStartTime,
        Long meetingEndTime,
        LocalDateTime createdAt,
        Set<DayOfWeek> selectedDays,
        Long chatRoomId
) {
    public static GroupBuyingBoardDetailDTO of(GroupBuyingBoard groupBuyingBoard) {
        Form form = groupBuyingBoard.getForm();
        ChatRoom chatRoom = groupBuyingBoard.getChatRoom();

        return new GroupBuyingBoardDetailDTO(
                groupBuyingBoard.getGroupBuyingBoardId(),
                groupBuyingBoard.getTitle(),
                groupBuyingBoard.getContent(),
                groupBuyingBoard.getImages().isEmpty() ? null : groupBuyingBoard.getImages().getFirst().getUrl(),
                groupBuyingBoard.getLikes(),
                groupBuyingBoard.getViews(),
                getFullAddress(form.getMeetingLocation()),
                form.getStartTime(), form.getEndTime(),
                groupBuyingBoard.getCreatedAt(),
                form.getSelectedDays(),
                chatRoom != null ? chatRoom.getChatRoomId() : null
        );
    }

    public static String getFullAddress(MeetingLocation meetingLocation) {
        return String.join(" ",
                meetingLocation.getSido(),
                meetingLocation.getSigungu(),
                meetingLocation.getEupmyeondong(),
                meetingLocation.getRi(),
                meetingLocation.getLoadName(),
                meetingLocation.getLoadNumber());
    }
}
