package com.ssafy.TogetherBuyMain.member.service;

import com.ssafy.TogetherBuyMain.global.AWS.S3Util;
import com.ssafy.TogetherBuyMain.global.exception.BusinessLogicException;
import com.ssafy.TogetherBuyMain.global.exception.ExceptionCode;
import com.ssafy.TogetherBuyMain.global.util.AddressUtil;
import com.ssafy.TogetherBuyMain.member.dto.request.MemberInitializeDTO;
import com.ssafy.TogetherBuyMain.member.dto.request.MemberRegisterRequestDTO;
import com.ssafy.TogetherBuyMain.member.dto.request.MyPageUpdateRequestDto;
import com.ssafy.TogetherBuyMain.member.dto.response.MemberRegisterResponseDTO;
import com.ssafy.TogetherBuyMain.member.dto.response.MyPageResponseDto;
import com.ssafy.TogetherBuyMain.member.entity.*;
import com.ssafy.TogetherBuyMain.member.repository.MemberImageRepository;
import com.ssafy.TogetherBuyMain.member.repository.MemberRepository;
import com.ssafy.TogetherBuyMain.security.jwt.service.JwtService;
import com.ssafy.TogetherBuyMain.security.jwt.util.JwtUtil;
import com.ssafy.TogetherBuyMain.security.oauth.util.OAuth2Util;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberImageRepository memberImageRepository;

    private final JwtUtil jwtUtil;
    private final JwtService jwtService;
    private final OAuth2Util oAuth2Util;
    private final S3Util s3Util;

    // JWT 토큰을 통한 회원가입
    public MemberRegisterResponseDTO   registerWithToken(
            String authorizationHeader,
            MemberRegisterRequestDTO request,
            HttpServletResponse response,
            MultipartFile file){

        // 토큰 추출
        String token = extractToken(authorizationHeader);

        // 토큰 유효성 검사
        jwtUtil.validateAccessToken(token);

        // 토큰에서 memberId뽑기.
        Long memberId = jwtUtil.getMemberIdFromToken(token);

        // 신규 회원 등록
        Member member = memberRepository.getReferenceById(memberId);
        MemberLocation memberLocation = AddressUtil.stringToEntity(request.address(), member);

        // S3에 이미지 업로드 및 URL 반환
        String imageUrl = s3Util.uploadFile(file, memberId);

        // 파일 이름 추출
        String fileName = extractFileName(file);
        String contentType = extractContentType(file);

        MemberImage profileImage = new MemberImage(
                null,
                imageUrl,
                fileName,
                contentType,
                member
        );

        // 엔티티에 request, location 적용
        member.signup(request, memberLocation, profileImage);

        // 저장
        member = memberRepository.save(member);

        // 토큰 발행해주고
        String accessToken = jwtService.generateAccessToken(member);

        addHeaderAccessToken(response, accessToken);

        return new MemberRegisterResponseDTO(member, imageUrl);
    }

    // 토큰 추출
    private String extractToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new BusinessLogicException(ExceptionCode.INVALID_TOKEN_FORMAT);
        }
        return authorizationHeader.substring(7);
    }

    private void addHeaderAccessToken(HttpServletResponse response, String accessToken) {
        jwtUtil.addTokenInCookie(response,"Authorization", accessToken, 60 * 60);
    }

    private String extractFileName(MultipartFile file) {
        return file.getOriginalFilename();
    }

    private String extractContentType(MultipartFile file) {
        return file.getContentType();
    }

    @Transactional
    public void updateMyPage(MyPageUpdateRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        // Member 정보 업데이트 (하나의 메서드로)
        member.updateMemberInfo(
                requestDto.getNickname(),
                requestDto.getTel(),
                requestDto.getBirth(),
                requestDto.getGender(),
                requestDto.getBankName(),
                requestDto.getBankNumber()
        );

        // MemberLocation 가져오거나 생성
        AddressUtil.stringToEntity(requestDto.getAddress(), member);

        // Seller 정보 업데이트
        if (requestDto.getSeller() != null && member.getSeller() != null) {
            member.getSeller().updateSellerInfo(
                    requestDto.getSeller().getBusinessName(),
                    requestDto.getSeller().getBossName(),
                    requestDto.getSeller().getTel(),
                    requestDto.getSeller().getBusinessEmail(),
                    requestDto.getSeller().getBusinessAddress(),
                    requestDto.getSeller().getBusinessNumber()
            );
        }
    }

    // 모바일 테스트용 더미 회원 넣기
    @Transactional
    public void loginTestMember(HttpServletResponse response) {
        Member testMember = Member.builder()
                .email("test@tset.com")
                .provider(Provider.KAKAO)
                .providerId("test_1234" + UUID.randomUUID())
                .build();

        testMember = memberRepository.save(testMember);
        Cookie accessCookie = new Cookie("Authorization", jwtService.generateAccessToken(testMember));
        Cookie refreshCookie = new Cookie("RefreshToken", jwtService.generateRefreshToken(testMember));
        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
    }

    private MemberLocation createMemberLocation(MyPageUpdateRequestDto requestDto, Member member) {
        // 주소를 파싱하여 AddressComponents 객체로 변환
        if (requestDto.getAddress() == null || requestDto.getAddress().isBlank()) {
            throw new BusinessLogicException(ExceptionCode.ADDRESS_EMPTY);
        }
        return AddressUtil.stringToEntity(requestDto.getAddress(), member);
    }

    @Transactional(readOnly = true)
    public MyPageResponseDto getMyPageInfo(String authorizationHeader) {

        String extractAccessToken = jwtUtil.extractToken(authorizationHeader);
        Long memberId = jwtUtil.getMemberIdFromToken(extractAccessToken);

        // Member 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        // Member 정보 빌드
        MyPageResponseDto.SellerInfo sellerInfo = null;
        if (member.getSeller() != null) {
            Seller seller = member.getSeller();

            sellerInfo = MyPageResponseDto.SellerInfo.builder()
                    .businessName(seller.getBusinessName())
                    .bossName(seller.getBossName())
                    .businessTel(seller.getBusinessTel())
                    .businessEmail(seller.getBusinessEmail())
                    .businessAddress(seller.getBusinessAddress())
                    .businessNumber(seller.getBusinessNumber())
                    .build();
        }

        String address = member.getMemberLocations().getFirst().fullAddress();

        // MyPageResponseDto 빌드
        return MyPageResponseDto.builder()
                .memberId(memberId)
                .nickname(member.getNickname())
                .tel(member.getTel())
                .birth(member.getBirth().toString())
                .gender(member.getGender())
                .address(address)
                .bankName(member.getBank())
                .bankNumber(member.getAccount())
                .seller(sellerInfo)
                .build();
    }
}
