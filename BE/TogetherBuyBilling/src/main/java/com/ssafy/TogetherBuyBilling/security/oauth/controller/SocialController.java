package com.ssafy.TogetherBuyBilling.security.oauth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.Map;
// 삭제예정 컨트롤러
@RestController
public class SocialController {

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    @GetMapping("/home")
    public String home() {
        return "로그인 성공!";
    }

    @GetMapping("/fail")
    public String fail() {
        return "로그인 실패xxxxxxx!";
    }


    @GetMapping("/signup")
    public String showSignUpForm() {
        return "회원가입 하세요!";
    }
}
