package com.ssafy.TogetherBuyMain.member.controller;

import com.ssafy.TogetherBuyMain.member.dto.request.MemberRegisterRequestDTO;
import com.ssafy.TogetherBuyMain.member.dto.request.MyPageUpdateRequestDto;
import com.ssafy.TogetherBuyMain.member.dto.response.MemberRegisterResponseDTO;
import com.ssafy.TogetherBuyMain.member.dto.response.MyPageResponseDto;
import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.member.entity.MemberPoint;
import com.ssafy.TogetherBuyMain.member.entity.Provider;
import com.ssafy.TogetherBuyMain.member.service.MemberService;
import com.ssafy.TogetherBuyMain.security.jwt.dto.response.JwtResponse;
import com.ssafy.TogetherBuyMain.security.jwt.handler.JwtException;
import com.ssafy.TogetherBuyMain.security.jwt.service.JwtService;
import com.ssafy.TogetherBuyMain.security.jwt.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final JwtService jwtService;
    private final MemberService memberService;

    // 회원가입 acesstoken으로 검사
    @PostMapping("/signup")
    public ResponseEntity<MemberRegisterResponseDTO> signup(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestPart("request") MemberRegisterRequestDTO request,
            @RequestPart("file") MultipartFile file,  // @RequestParam으로 파일을 받도록 수정
            HttpServletResponse response){

        MemberRegisterResponseDTO responseDTO = memberService.registerWithToken(authorizationHeader, request, response, file);
        return ResponseEntity.ok(responseDTO);
    }

    // Access로 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestHeader("Authorization") String authorizationHeader){
        return ResponseEntity.noContent().build();
    }

    // Access Token만 재발급 / Refresh Token 유효시 재발급
    @PostMapping("/reissue/access-token")
    public ResponseEntity<Void> reissueAccessTokenIfRefreshValid(@RequestHeader("Refresh-Token") String refreshToken,
                                                                 HttpServletResponse response){
        jwtService.reissueAccessTokenAndCheckValid(refreshToken, response);
        return ResponseEntity.noContent().build();
    }

    // logout
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Refresh-Token") String refreshToken,
            HttpServletResponse response) {
        jwtService.logout(refreshToken, response);
        return ResponseEntity.noContent().build();
    }

    // 마이페이지 조회
    @GetMapping("/mypage")
    public ResponseEntity<MyPageResponseDto> getMyPage(@RequestHeader("Authorization") String authorizationHeader) {
        MyPageResponseDto responseDto = memberService.getMyPageInfo(authorizationHeader);
        return ResponseEntity.ok(responseDto);
    }

    // 마이페이지 수정
    @PutMapping("/mypage")
    public ResponseEntity<Void> updateMyPage(@RequestHeader("Authorization") String authorizationHeader,
                                             @RequestBody MyPageUpdateRequestDto requestDto) {
        memberService.updateMyPage(requestDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/test/login")
    public ResponseEntity<?> testLogin(HttpServletResponse response) {
        memberService.loginTestMember(response);
        return ResponseEntity.ok().build();
    }
}
