package com.ssafy.TogetherBuyMain.global.util;

import com.ssafy.TogetherBuyMain.member.entity.Member;
import com.ssafy.TogetherBuyMain.member.repository.MemberRepository;
import com.ssafy.TogetherBuyMain.security.jwt.util.JwtUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class MemberUtil {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    public Member getMember(String accessToken) {
        Long memberId = jwtUtil.getMemberIdFromToken(accessToken);
        return memberRepository.findById(memberId).orElse(null);
    }

    public Member getMemberByAuthorization(String authorization) {
        String accessToken = jwtUtil.extractToken(authorization);
        return getMember(accessToken);
    }
}
