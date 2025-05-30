package com.ssafy.TogetherBuyMain.community.dto.groupBuyingBoard;

import com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoard;
import com.ssafy.TogetherBuyMain.shop.entity.common.DayOfWeek;
import com.ssafy.TogetherBuyMain.shop.entity.common.Form;
import com.ssafy.TogetherBuyMain.shop.entity.common.MeetingLocation;
import com.ssafy.TogetherBuyMain.shop.entity.product.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public record ExpiringGroupBuyingBoardDTO(
        Long groupBuyingBoardId,
        String groupBuyingBoardTitle,
        Long currentAmount,
        Long totalAmount,
        String meetingLocation,
        Long meetingStartTime,
        Long meetingEndTime,
        String productMainImage,
        List<String> dayOfWeek
) {
    public static ExpiringGroupBuyingBoardDTO from(Object[] row) {
        return new ExpiringGroupBuyingBoardDTO(
                ((Number) row[0]).longValue(),
                (String) row[1],
                ((Number) row[2]).longValue(),
                ((Number) row[3]).longValue(),
                (String) row[4],
                ((Number) row[5]).longValue(),
                ((Number) row[6]).longValue(),
                (String) row[7],
                row[8] != null ? Arrays.asList(((String) row[8]).split(",")) : new ArrayList<>()
        );
    }
}

//public record ExpiringGroupBuyingBoardDTO(
//        Long groupBuyingBoardId,
//        String groupBuyingBoardTitle,
//        Long currentAmount,
//        Long totalAmount,
//        String meetingLocation,
//        Long meetingStartTime,
//        Long meetingEndTime,
//        String productMainImage,
//        List<String> dayOfWeek
//
//) {
//    public static ExpiringGroupBuyingBoardDTO of(GroupBuyingBoard groupBuyingBoard) {
//        Form form = groupBuyingBoard.getForm();
//        Product product = groupBuyingBoard.getProduct();
//
//        String imageUrl = product.getImages().isEmpty() ? null : product.getImages().getFirst().getUrl();
//        List<String> dayOfWeekList = form.getSelectedDays().stream()
//                .map(DayOfWeek::getKoreanName)
//                .collect(Collectors.toList());
//
//        return new ExpiringGroupBuyingBoardDTO(
//                groupBuyingBoard.getGroupBuyingBoardId(),
//                groupBuyingBoard.getTitle(),
//                form.getCurrentAmount(),
//                product.getTotal(),
//                getFullAddress(form.getMeetingLocation()),
//                form.getStartTime(), form.getEndTime(),
//                imageUrl,
//                dayOfWeekList
//        );
//    }
//
//    public static String getFullAddress(MeetingLocation meetingLocation) {
//        return String.join(" ",
//                meetingLocation.getSido(),
//                meetingLocation.getSigungu(),
//                meetingLocation.getEupmyeondong(),
//                meetingLocation.getRi(),
//                meetingLocation.getLoadName(),
//                meetingLocation.getLoadNumber());
//    }
//}
