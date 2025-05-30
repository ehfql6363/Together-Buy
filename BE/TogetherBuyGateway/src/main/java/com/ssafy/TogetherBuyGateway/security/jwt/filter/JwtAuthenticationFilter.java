package com.ssafy.TogetherBuyGateway.security.jwt.filter;

import com.ssafy.TogetherBuyGateway.global.exception.ExceptionCode;
import com.ssafy.TogetherBuyGateway.member.entity.Member;
import com.ssafy.TogetherBuyGateway.member.entity.Provider;
import com.ssafy.TogetherBuyGateway.member.repository.MemberRepository;
import com.ssafy.TogetherBuyGateway.security.jwt.handler.JwtException;
import com.ssafy.TogetherBuyGateway.security.jwt.service.JwtService;
import com.ssafy.TogetherBuyGateway.security.jwt.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. "Refresh-Token"이 헤더에 있으면 필터를 건너뛰고 다음 필터로 전달
        if (request.getHeader("Refresh-Token") != null) {
            filterChain.doFilter(request, response);
            return;
        } else {

        String token = null;
        String providerId = null;

        // 2. "Refresh-Token"이 없을 경우, "Authorization" 헤더에서 Access Token을 추출
        String authHeader = request.getHeader("Authorization");

        // 3. Authorization 헤더가 존재하고, "Bearer "로 시작하는지 확인
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);  // "Bearer " 이후 값인 JWT 토큰을 추출
            providerId = jwtService.getUserIdFromAccessToken(token);  // 토큰에서 사용자 ID를 추출
        }

        // 4. SecurityContext에 인증 정보가 없다면
        if (providerId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 사용자 정보를 조회
            Member member = memberRepository.findByProviderId(providerId)
                    .orElseThrow(() -> new JwtException(ExceptionCode.MEMBER_NOT_FOUND));

            UserDetails userDetails = userDetailsService.loadUserByUsername(providerId);

            // JWT 토큰의 유효성 검사
            if (jwtService.validateToken(token)) {
                // 인증 정보를 SecurityContext에 설정
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // 5. 인증정보가 있으면, JWT 토큰의 유효성 검사 및 사용자 정보 확인
        if (providerId != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            Long tokenMemberId = jwtUtil.getMemberIdFromToken(token);
            String providerStr = jwtUtil.getProviderFromAccessToken(token);
            Provider providerEnum = Provider.toEnum(providerStr);

            Member member = memberRepository.findByProviderAndProviderId(providerEnum, providerId)
                    .orElseThrow(() -> new JwtException(ExceptionCode.MEMBER_NOT_FOUND));

            if (tokenMemberId.equals(member.getMemberId())) {
                // DB에서 최신 멤버 정보를 조회
                Member updatedMember = memberRepository.findById(member.getMemberId())
                        .orElseThrow(() -> new JwtException(ExceptionCode.MEMBER_NOT_FOUND));

                // 새로운 Access Token과 Refresh Token 생성
                String newAccessToken = jwtUtil.generateAccessToken(updatedMember);
                String newRefreshToken = jwtUtil.generateRefreshToken(updatedMember);

                // 2. 새로운 토큰을 쿠키에 담고, 기존 쿠키 삭제

                // 기존 쿠키 삭제 (옵션: 필요에 따라 구현)
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("Authorization") || cookie.getName().equals("Refresh-Token")) {
                            cookie.setMaxAge(0); // 쿠키 만료
                            cookie.setPath("/");
                            response.addCookie(cookie);
                        }
                    }
                }

                jwtUtil.addCookie(response, "Authorization", newAccessToken, 3600);  // 예: 1시간 유효
                jwtUtil.addCookie(response, "Refresh-Token", newRefreshToken, 86400);  // 예: 24시간 유효

            } else {
                throw new JwtException(ExceptionCode.MEMBER_ID_MISMATCH);
            }
        }

        // 7. 필터 체인을 통해 다음 필터로 요청을 전달
        filterChain.doFilter(request, response);
    }}
}