package com.ssafy.TogetherBuyMain.shop.controller;

import com.ssafy.TogetherBuyMain.shop.dto.BooleanResponseDTO;
import com.ssafy.TogetherBuyMain.shop.dto.form.*;
import com.ssafy.TogetherBuyMain.shop.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class FormController {

    private final FormService formService;

    // 공구 신청서 작성
    @PostMapping("/group-buying/{boardId}")
    public ResponseEntity<FormRegisterResponseDTO> registerForm(
            @PathVariable("boardId") Long boardId,
            @RequestBody FormRegisterRequestDTO requestDTO,
            @RequestHeader("Authorization") String authorizationHeader) {

        FormRegisterResponseDTO responseDTO = formService.registerForm(boardId, requestDTO, authorizationHeader);

        return ResponseEntity.ok(responseDTO);
    }

    // 신청서 조회
    @GetMapping("/group-buying/{boardId}")
    public ResponseEntity<?> retrieveForm(
            @PathVariable("boardId") Long boardId) {
        FormDetailResponseDTO response = formService.getFormDetail(boardId);
        return ResponseEntity.ok(response);
    }

    // 공구 참여
    @PostMapping("/group-buying/{boardId}/apply")
    public ResponseEntity<ApplyFormRegisterResponseDTO> applyForm(
            @PathVariable("boardId") Long boardId,
            @RequestBody ApplyFormRegisterRequestDTO requestDTO,
            @RequestHeader("Authorization") String authorizationHeader) {

        ApplyFormRegisterResponseDTO responseDTO = formService.applyForm(boardId, requestDTO, authorizationHeader);

        return ResponseEntity.ok(responseDTO);
    }

    // 공구 참여 취소
    @DeleteMapping("/group-buying/{boardId}/apply")
    public ResponseEntity<BooleanResponseDTO> undoForm(
            @PathVariable("boardId") Long boardId,
            @RequestHeader("Authorization") String authorizationHeader) {

        BooleanResponseDTO responseDTO = formService.undoApply(boardId, authorizationHeader);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/{formId}/recipient/{recipientId}")
    public ResponseEntity<?> setRecipient(
            @PathVariable("formId") Long formId,
            @PathVariable("recipientId") Long recipientId) {
        RecipientResponseDTO response = formService.setRecipient(formId, recipientId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{formId}/day-of-week")
    public ResponseEntity<?> updateDayOfWeek(
            @PathVariable("formId") Long formId,
            @RequestBody UpdateDaysOfWeekRequestDTO request) {
        DaysOfWeekResponseDTO response = formService.updateDayOfWeek(formId, request);
        return ResponseEntity.ok(response);
    }

}
