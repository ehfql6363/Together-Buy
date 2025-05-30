package com.ssafy.TogetherBuyGateway.member.service;

import com.ssafy.TogetherBuyGateway.global.exception.BusinessLogicException;
import com.ssafy.TogetherBuyGateway.global.exception.ExceptionCode;
import com.ssafy.TogetherBuyGateway.member.repository.MemberImageRepository;
import com.ssafy.TogetherBuyGateway.member.repository.MemberRepository;
import com.ssafy.TogetherBuyGateway.security.jwt.service.JwtService;
import com.ssafy.TogetherBuyGateway.security.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberImageRepository memberImageRepository;

    private final JwtUtil jwtUtil;
    private final JwtService jwtService;

    // 토큰 추출
    private String extractToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new BusinessLogicException(ExceptionCode.INVALID_TOKEN_FORMAT);
        }
        return authorizationHeader.substring(7);
    }


    private String extractFileName(MultipartFile file) {
        return file.getOriginalFilename();
    }

    private String extractContentType(MultipartFile file) {
        return file.getContentType();
    }
}
