package com.ssafy.TogetherBuyMain.shop.service;

import com.ssafy.TogetherBuyMain.community.entity.groupBuyingBoard.GroupBuyingBoard;
import com.ssafy.TogetherBuyMain.community.repository.groupBuyingBoard.GroupBuyingBoardRepository;
import com.ssafy.TogetherBuyMain.global.exception.BusinessLogicException;
import com.ssafy.TogetherBuyMain.global.exception.ExceptionCode;
import com.ssafy.TogetherBuyMain.global.util.AddressUtil;
import com.ssafy.TogetherBuyMain.member.repository.MemberRepository;
import com.ssafy.TogetherBuyMain.shop.dto.form.*;
import com.ssafy.TogetherBuyMain.shop.entity.order.DeliveryStatus;
import com.ssafy.TogetherBuyMain.shop.entity.order.Order;
import com.ssafy.TogetherBuyMain.shop.entity.product.Product;
import com.ssafy.TogetherBuyMain.shop.repository.common.ApplyRepository;
import com.ssafy.TogetherBuyMain.global.util.MemberUtil;
import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.security.jwt.util.JwtUtil;
import com.ssafy.TogetherBuyMain.shop.dto.BooleanResponseDTO;
import com.ssafy.TogetherBuyMain.shop.entity.common.*;
import com.ssafy.TogetherBuyMain.shop.repository.common.FormRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormService {

    private final JwtUtil jwtUtil;
    private final MemberUtil memberUtil;

    private final FormRepository formRepository;
    private final GroupBuyingBoardRepository groupBuyingBoardRepository;
    private final ApplyRepository applyRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public FormRegisterResponseDTO registerForm(
            Long boardId,
            FormRegisterRequestDTO requestDTO,
            String authorizationHeader) {
        try {
            String accessToken = jwtUtil.extractToken(authorizationHeader);
            Member member = memberUtil.getMember(accessToken);
            if (member == null) throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);

            GroupBuyingBoard groupBuyingBoard = groupBuyingBoardRepository.findById(boardId)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.GROUP_BUYING_BOARD_NOT_FOUND));


            Form newForm = createForm(requestDTO, groupBuyingBoard);
            updateDayOfWeek(requestDTO.dayOfWeeks(), newForm);

            Form form = formRepository.save(newForm); // 영속화

            Apply apply = createApply(form, member, groupBuyingBoard, requestDTO.amount());
            addApply(form, apply);

            applyRepository.save(apply);

            member.purchase(apply.getCost());

            return new FormRegisterResponseDTO(form.getFormId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessLogicException(ExceptionCode.FORM_REGISTRATION_FAILED);
        }
    }

    @Transactional
    public FormDetailResponseDTO getFormDetail(Long boardId) {
        try {
            GroupBuyingBoard groupBuyingBoard = groupBuyingBoardRepository.findById(boardId)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.GROUP_BUYING_BOARD_NOT_FOUND));
            Form form = groupBuyingBoard.getForm();
            if (form == null) throw new BusinessLogicException(ExceptionCode.FORM_NOT_FOUND);

            return FormDetailResponseDTO.of(form);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessLogicException(ExceptionCode.FORM_RETREIVE_FAILED);
        }
    }

    @Transactional
    public ApplyFormRegisterResponseDTO applyForm(
            Long boardId,
            ApplyFormRegisterRequestDTO requestDTO,
            String authorizationHeader) {
        try {
            String accessToken = jwtUtil.extractToken(authorizationHeader);
            Member member = memberUtil.getMember(accessToken);
            if (member == null) throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);

            GroupBuyingBoard groupBuyingBoard = groupBuyingBoardRepository.findById(boardId)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.GROUP_BUYING_BOARD_NOT_FOUND));

            Form form = groupBuyingBoard.getForm();
            if(form == null) throw new BusinessLogicException(ExceptionCode.FORM_NOT_FOUND);

            Long requestAmount = requestDTO.amount();
            Long totalCost = requestAmount * groupBuyingBoard.getProduct().getPricePerUnit();

            // 폼 상태 예외 처리 (잔여 수량 초과)
            if (form.getCurrentAmount() + requestAmount > form.getTotalAmount()) {
                throw new BusinessLogicException(ExceptionCode.APPLY_REGISTRATION_FAILED);
            }

            // 사용자 포인트 차감
            member.purchase(totalCost);

            Apply apply = createApply(form, member, groupBuyingBoard, requestDTO);
            addApply(form, apply);

            return new ApplyFormRegisterResponseDTO(form.getFormId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessLogicException(ExceptionCode.APPLY_REGISTRATION_FAILED);
        }
    }

    @Transactional
    public BooleanResponseDTO undoApply(
            Long boardId,
            String authorizationHeader) {
        try {
            String accessToken = jwtUtil.extractToken(authorizationHeader);
            Member member = memberUtil.getMember(accessToken);
            if (member == null) throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);

            GroupBuyingBoard groupBuyingBoard = groupBuyingBoardRepository.findById(boardId)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.GROUP_BUYING_BOARD_NOT_FOUND));

            Form form = groupBuyingBoard.getForm();
            if (form == null) throw new BusinessLogicException(ExceptionCode.FORM_NOT_FOUND);

            Apply targetApply = applyRepository.findByFormAndMember(form, member)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.APPLY_NOT_FOUND));

            form.decreaseCurrentAmount(targetApply.getAmount());
            applyRepository.delete(targetApply);

            return new BooleanResponseDTO(true);
        }catch (Exception e) {
            e.printStackTrace();
            throw new BusinessLogicException(ExceptionCode.APPLY_UNDO_FAILED);
        }
    }

    @Transactional
    public BooleanResponseDTO startOrder(String authorizationHeader, Long formId) {
        try {
            String accessToken = jwtUtil.extractToken(authorizationHeader);
            Member member = memberUtil.getMember(accessToken);
            if (member == null) throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);

            Form form = formRepository.findById(formId)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.FORM_NOT_FOUND));

            if(!Objects.equals(form.getGroupBuyingBoard().getCreator().getMemberId(), member.getMemberId()))
                throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_AUTHORIZED);

            if(!form.isReadyForOrder())
                throw new BusinessLogicException(ExceptionCode.INVAILD_FORM_STATUS_NOT_MATCHED_AMOUNT);

            if(form.getRecipient() == null)
                throw new BusinessLogicException(ExceptionCode.INVAILD_FORM_STATUS_NOT_FOUND_RECIPIENT);

            Order order = createNewOrder(form, member);

            form.order(order);
            formRepository.save(form);

            return new BooleanResponseDTO(true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessLogicException(ExceptionCode.ORDER_START_FAILED);
        }
    }

    @Transactional
    public RecipientResponseDTO setRecipient(Long formId, Long recipientId) {
        try {
            Form form = formRepository.findById(formId)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.FORM_NOT_FOUND));
            Member recipient = memberRepository.findById(recipientId)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

            form.updateRecipient(recipient);

            return RecipientResponseDTO.of(recipient);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessLogicException(ExceptionCode.SET_RECIPIENT_FAILED);
        }
    }

    @Transactional
    public DaysOfWeekResponseDTO updateDayOfWeek(
            Long formId,
            UpdateDaysOfWeekRequestDTO request
    ) {
        try {
            Form form = formRepository.findById(formId)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.FORM_NOT_FOUND));

            updateDayOfWeek(request.daysOfWeek(), form);

            return DaysOfWeekResponseDTO.of(request.daysOfWeek());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessLogicException(ExceptionCode.SET_DAY_OF_WEEK_FAILED);
        }
    }

    private Form createForm(FormRegisterRequestDTO requestDTO, GroupBuyingBoard groupBuyingBoard) {
        Matcher matcher = AddressUtil.getParsingAddressArray(requestDTO.address());
        MeetingLocation location = MeetingLocation.builder()
                .sido(matcher.group("CityOrProvince"))
                .sigungu(matcher.group("CityOrCounty"))
                .eupmyeondong(matcher.group("TownOrDistrict"))
                .ri(matcher.group("Village"))
                .loadName(matcher.group("RoadName"))
                .loadNumber(matcher.group("RoadNumber"))
                .build();

        return Form.builder()
                .formId(null)
                .totalAmount(groupBuyingBoard.getProduct().getTotal())
                .currentAmount(0L)
                .selectedDays(new HashSet<>())
                .meetingLocation(location)
                .startTime(requestDTO.startTime())
                .endTime(requestDTO.endTime())
                .applies(new ArrayList<>())
                .groupBuyingBoard(groupBuyingBoard)
                .build();
    }

    private void updateDayOfWeek(List<String> dayOfWeekList, Form form) {
        if (dayOfWeekList.isEmpty()) throw new BusinessLogicException(ExceptionCode.DAY_OF_WEEK_NOT_FOUND);
        Set<DayOfWeek> dayOfWeeks = dayOfWeekList.stream()
                .map(DayOfWeek::valueOf)
                .collect(Collectors.toSet());

        form.updateDayOfWeek(dayOfWeeks);
    }

    private Apply createApply(Form form, Member member, GroupBuyingBoard groupBuyingBoard, Long amount) {
        Long cost = amount * groupBuyingBoard.getProduct().getPricePerUnit();
        return Apply.builder()
                .applyId(null)
                .form(form)
                .member(member)
                .groupBuyingBoard(groupBuyingBoard)
                .status(ApplyStatus.NON_RECEIPT)
                .appliedAt(LocalDate.now())
                .amount(amount)
                .cost(cost)
                .build();
    }

    private void addApply(Form form, Apply apply) {
        form.getApplies().add(apply);
        form.increaseCurrentAmount(apply.getAmount());
    }

    private Apply createApply(Form form, Member member, GroupBuyingBoard groupBuyingBoard, ApplyFormRegisterRequestDTO requestDTO) {
        long remainingAmount = form.getTotalAmount() - form.getCurrentAmount();
        Long amount = requestDTO.amount();
        if(remainingAmount < amount)
            throw new IllegalArgumentException("구매할 수 있는 개수를 초과했습니다.");

        return createApply(form, member, groupBuyingBoard, amount);
    }

    private Order createNewOrder(Form form, Member member) {
        Product product = form.getGroupBuyingBoard().getProduct();

        return Order.builder()
                .orderAddress(form.getRecipient()
                        .getMemberLocations().getFirst()
                        .getLocation())
                .orderDate(LocalDate.now())
                .member(member)
                .deliveryStatus(DeliveryStatus.BEFORE)
                .product(product)
                .seller(product.getSeller())
                .form(form)
                .orderPrice(product.getSalePrice())
                .orderQuantity(form.getTotalAmount())
                .build();
    }
}
