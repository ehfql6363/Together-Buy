package com.ssafy.TogetherBuyMain.community.dto.groupBuyingBoard;

import com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoard;
import com.ssafy.TogetherBuyMain.shop.entity.common.*;

import java.util.List;
import java.util.Set;

public record AppliedGroupBuyingBoardDTO(
        Long groupBuyingBoardId,
        String groupBuyingBoardTitle,
        String meetingLocation,
        Long meetingStartTime,
        Long meetingEndTime,
        String productMainImage,
        Long totalAmount,
        Long currAmount,
        Long chatRoomId,
        Set<DayOfWeek> selectedDays
) {
    public static AppliedGroupBuyingBoardDTO of(Apply apply) {
        GroupBuyingBoard groupBuyingBoard = apply.getGroupBuyingBoard();
        Form form = groupBuyingBoard.getForm();
        List<ProductImage> productImages = groupBuyingBoard.getProduct().getImages();
        return new AppliedGroupBuyingBoardDTO(
                groupBuyingBoard.getGroupBuyingBoardId(),
                groupBuyingBoard.getTitle(),
                getFullAddress(form.getMeetingLocation()),
                form.getStartTime(),
                form.getEndTime(),
                productImages.isEmpty() ? null : productImages.getFirst().getUrl(),
                form.getTotalAmount(),
                form.getCurrentAmount(),
                groupBuyingBoard.getChatRoom() != null ? groupBuyingBoard.getChatRoom().getChatRoomId() : null,
                form.getSelectedDays()
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
