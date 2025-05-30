package com.ssafy.TogetherBuyGateway.security.service;

import com.ssafy.TogetherBuyGateway.global.exception.ExceptionCode;
import com.ssafy.TogetherBuyGateway.member.entity.Member;
import com.ssafy.TogetherBuyGateway.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String providerId) throws UsernameNotFoundException {
        // 이메일로 사용자 조회
        Member member = memberRepository.findByProviderId(providerId)
                .orElseThrow(() -> new UsernameNotFoundException(ExceptionCode.MEMBER_NOT_FOUND.getMessage()));

        // UserDetails 객체 생성
        return User.builder()
                .username(member.getEmail())
                .roles("USER") // MemberType을 역할로 사용
                .password("")
                .build();
    }
}
