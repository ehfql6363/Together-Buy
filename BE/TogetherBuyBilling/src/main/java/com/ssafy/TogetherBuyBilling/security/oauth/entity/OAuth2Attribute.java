package com.ssafy.TogetherBuyBilling.security.oauth.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.Map;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부에서 생성 불가하게 PROTECTED설정
@Getter
@Entity
@Builder
public class OAuth2Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider; // OAuth2 제공자(예: Google, Kakao)를 저장하는 필드
    private String providerId;
    private String email;
    private String nickname;

    @Transient // 데이터 베이스에 저장되지 않음을 나타냄 (임시 필드)
    private String attributeKey; // OAuth2 제공자로부터 받은 사용자 정보의 키를 저장함.

    @Transient //(임시 필드)
    private Map<String, Object> attributes; //  OAuth2 제공자로부터 받은 사용자 정보를 저장 필드

    // OAuth2UserRequest와 OAuth2User를 기반으로 OAuth2Attribute 객체를 생성
    public static OAuth2Attribute of(OAuth2UserRequest request, OAuth2User user){
        String registrationId = request.getClientRegistration().getRegistrationId(); //OAuth2 제공자의 등록 ID
        String attributeName = request.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); // 사용자 정보의 키
        /*
        사용자의 고유 식별자(예: 이메일, 사용자 ID 등)를 추출하기 위해 사용되는 키
        OAuth2 제공자는 사용자 정보를 JSON 형태로 반환하는데, 이 JSON에서 어떤 필드를 사용자의 고유 식별자로 사용할지 결정하는 것이 attributeName
        예를 들어, Google의 경우 attributeName은 "sub" (사용자의 고유 ID)가 될 수 있고, Facebook의 경우 "id"가 될 수 있습니다. 이 값은 OAuth2 제공자의 설정에 따라 다름
        attributeName은 "sub", "id", "email" 등이 될 수 있다.
         */

        OAuth2Attribute oAuth2Attribute = of(user, registrationId, attributeName);

        return oAuth2Attribute;
    }

    //네이버 객체
    private static OAuth2Attribute of(OAuth2User user, String provider, String attributeKey) {

        Map<String, Object> attributes = user.getAttributes();
        Map<String, Object> response = (Map<String, Object>) attributes.get("response"); // 네이버는 response
        String nickname = Optional.ofNullable((String) response.get("name")).orElse("");
        String email = Optional.ofNullable((String) response.get("email")).orElse("");

        // Long 처리 부분 수정 필요해보임
        Long id = Optional.ofNullable((String) response.get("id"))
                .map(idStr -> {
                    try {
                        return Long.valueOf(idStr);
                    } catch (NumberFormatException e) {
                        // 숫자 형식이 아니면 기본값 처리
                        return null;
                    }
                }).orElse(null);

        return OAuth2Attribute.builder()
                .id(id) // 네이버의 고유 ID
                .attributeKey(attributeKey)
                .attributes(attributes)
                .provider(provider)
                .email(email)
                .nickname(nickname)
                .build();
    }
}
