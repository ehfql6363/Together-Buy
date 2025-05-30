package com.ssafy.TogetherBuyMain.shop.dto.form;

import com.ssafy.TogetherBuyMain.shop.entity.common.Apply;
import com.ssafy.TogetherBuyMain.shop.entity.common.DayOfWeek;
import com.ssafy.TogetherBuyMain.shop.entity.common.Form;

import java.util.*;

public record FormDetailResponseDTO(
        Long totalAmount,
        Long currentAmount,
        Set<DayOfWeek> dayOfWeeks,
        Long startTime,
        Long endTime,
        List<ParticipantResponseDTO> participants,
        Long size
) {
    public static FormDetailResponseDTO of(Form form) {
        List<Apply> applies = form.getApplies();
        List<ParticipantResponseDTO> participants = getRecipients(applies);

        return new FormDetailResponseDTO(
                form.getTotalAmount(),
                form.getCurrentAmount(),
                form.getSelectedDays(),
                form.getStartTime(),
                form.getEndTime(),
                participants,
                participants == null ? 0 : (long) participants.size());
    }

    private static List<ParticipantResponseDTO> getRecipients(List<Apply> applies) {
        if(applies.isEmpty()) return null;

        Map<Long, List<Apply>> map = new HashMap<>();
        for(Apply apply : applies) {
            Long id = apply.getMember().getMemberId();
            List<Apply> personalApplies = map.getOrDefault(id, new ArrayList<>());
            personalApplies.add(apply);
            map.put(id, personalApplies);
        }

        List<ParticipantResponseDTO> participants = new ArrayList<>();
        for(Map.Entry<Long, List<Apply>> entry : map.entrySet()) {
            for(Apply apply : entry.getValue()) {
                participants.add(ParticipantResponseDTO.of(apply));
            }
        }

        return participants;
    }
}
