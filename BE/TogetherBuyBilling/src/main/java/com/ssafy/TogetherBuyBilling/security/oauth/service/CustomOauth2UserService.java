package com.ssafy.TogetherBuyBilling.security.oauth.service;

import com.ssafy.TogetherBuyBilling.member.repository.MemberRepository;
import com.ssafy.TogetherBuyBilling.security.jwt.service.JwtService;
import com.ssafy.TogetherBuyBilling.security.oauth.factory.CustomOAuth2User;
import com.ssafy.TogetherBuyBilling.security.oauth.entity.OAuth2Attribute;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// 애플리케이션 내부의 사용자 정보 처리(Oauth2의 사용자 정보를 애플리케이션 내부의 정보와 매핑하고 등록하는 기능)
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> userService = new DefaultOAuth2UserService();

        // OAuth2 제공자로부터 사용자 정보 로드
        OAuth2User oAuth2User = userService.loadUser(request);

        // oAuth2User을 애플리케이션에서 사용할 수 있는 형태로 변환하는 코드.
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(request, oAuth2User);

        return CustomOAuth2User.of(oAuth2Attribute);
    }
}