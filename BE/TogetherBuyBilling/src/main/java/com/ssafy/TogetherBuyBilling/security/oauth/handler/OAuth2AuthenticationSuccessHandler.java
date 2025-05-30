package com.ssafy.TogetherBuyBilling.security.oauth.handler;

import com.ssafy.TogetherBuyBilling.member.entity.Member;
import com.ssafy.TogetherBuyBilling.member.entity.Provider;
import com.ssafy.TogetherBuyBilling.member.repository.MemberRepository;
import com.ssafy.TogetherBuyBilling.security.jwt.service.RefreshTokenService;
import com.ssafy.TogetherBuyBilling.security.jwt.util.JwtUtil;
import com.ssafy.TogetherBuyBilling.security.oauth.entity.OAuth2Attribute;
import com.ssafy.TogetherBuyBilling.security.oauth.factory.OAuth2UserFactory;
import com.ssafy.TogetherBuyBilling.security.oauth.util.OAuth2Util;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final OAuth2UserFactory oAuth2UserFactory;
    private final OAuth2Util oauth2Util;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // min example
        OAuth2Attribute userInfo = oAuth2UserFactory.getOAuth2UserInfo(authentication);

        // OAuth2Attribute에서 Member 생성 또는 조회
        Optional<Member> existingMember = memberRepository.findByProviderAndProviderId(
                Provider.toEnum(userInfo.getProvider()),
                userInfo.getProviderId()); // null

        Member member;

        String[] tokens = new String[2];

        if (existingMember.isPresent()) { // 신규 유저의 회원 가입 or 휴면 회원(리프래시 토큰 재발급)
            member = existingMember.get();
            boolean isValid = oauth2Util.isRefreshTokenValid(member);
            if (isValid) {
                tokens[0] = oauth2Util.getAccessToken(member);
                tokens[1] = member.getRefreshToken().getRefreshToken();
            } else {
                tokens = oauth2Util.getTokens(member);
            }
        } else {
            // 신규 회원 생성 및 DB에 저장
            member = Member.builder()
                    .email(userInfo.getEmail())
                    .provider(Provider.toEnum(userInfo.getProvider()))  // 변환 적용
                    .providerId(userInfo.getProviderId())
                    .build();

            member = memberRepository.saveAndFlush(member);
            tokens = oauth2Util.handleNewMember(member, response);
        }

        jwtUtil.addTokenInCookie(response, "Authorization", tokens[0], 60 * 60);
        jwtUtil.addTokenInCookie(response, "Refresh-Token", tokens[1], 60 * 60 * 24 * 7); //  * 24 * 7 // 7일 유지

        // String redirectUrl = "http://localhost:8080/home";
//        String redirectUrl = "http://localhost:3000/auth/callback"; // 로컬 테스트용  url
        String redirectUrl = "https://togethrebuy.com/auth/callback";
        //todo: redirectUrl 에 내가 보내주고 싶은 위치로 보내주면 된다....
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
