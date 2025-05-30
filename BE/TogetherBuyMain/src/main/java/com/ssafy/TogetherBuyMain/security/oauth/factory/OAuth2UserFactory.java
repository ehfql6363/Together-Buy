package com.ssafy.TogetherBuyMain.security.oauth.factory;

import com.ssafy.TogetherBuyMain.security.oauth.entity.OAuth2Attribute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserFactory {

    public OAuth2Attribute getOAuth2UserInfo(Authentication authentication) {
        if (authentication.getPrincipal() instanceof DefaultOidcUser) {
            return extractGoogleUserInfo((DefaultOidcUser) authentication.getPrincipal());
        } else if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            return extractGoogleUserInfo((DefaultOAuth2User) authentication.getPrincipal());
        } else if (authentication.getPrincipal() instanceof CustomOAuth2User) {
            return extractNaverUserInfo((CustomOAuth2User) authentication.getPrincipal());
        }
        log.error("Unexpected principal type: {}", authentication.getPrincipal().getClass().getName());
        throw new OAuth2AuthenticationException("Unsupported OAuth2 provider");
    }

    private OAuth2Attribute extractGoogleUserInfo(DefaultOAuth2User oauth2User) {
        Map<String, Object> attributes = oauth2User.getAttributes();

        return OAuth2Attribute.builder()
                .providerId((String) attributes.get("sub"))  // Google의 고유 사용자 ID
                .email((String) attributes.get("email"))
                .nickname((String) attributes.get("name"))
                .provider("google")
                .build();
    }

    private OAuth2Attribute extractNaverUserInfo(CustomOAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2Attribute.builder()
                .providerId((String) response.get("id"))
                .email((String) response.get("email"))
                .nickname((String) response.get("name"))
                .provider("naver")
                .build();
    }
}
