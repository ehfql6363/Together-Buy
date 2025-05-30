package com.ssafy.TogetherBuyBilling.security.oauth.factory;

import com.ssafy.TogetherBuyBilling.security.oauth.entity.OAuth2Attribute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@ToString
@Setter
@Getter
@AllArgsConstructor
public class CustomOAuth2User implements OAuth2User, Serializable{

    private Map<String, Object> attributes;
    private String attributeKey;

    public static CustomOAuth2User of(OAuth2Attribute oAuth2Attribute){
        return new CustomOAuth2User(oAuth2Attribute.getAttributes(), oAuth2Attribute.getAttributeKey());
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getName() {
        return String.valueOf(attributes.get(attributeKey));
    }
}
