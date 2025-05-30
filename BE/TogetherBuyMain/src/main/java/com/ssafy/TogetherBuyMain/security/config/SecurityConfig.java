package com.ssafy.TogetherBuyMain.security.config;

import com.ssafy.TogetherBuyMain.security.jwt.filter.JwtAuthenticationFilter;
import com.ssafy.TogetherBuyMain.security.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.ssafy.TogetherBuyMain.security.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.ssafy.TogetherBuyMain.security.oauth.service.CustomOauth2UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOauth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler successHandler;
    private final OAuth2AuthenticationFailureHandler failureHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // CSRF 비활성화
                .csrf(csrf -> csrf.disable())
//                .cors(cors -> cors.disable())

                // CORS 설정 사용
//                .cors(Customizer.withDefaults())

                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))

                // 세션 사용 안 함
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        // 🔹 인증 없이 접근 가능한 엔드포인트
                        .requestMatchers("/", "/home", "/signup", "api/login", "/member/**").permitAll()
                        .requestMatchers("/oauth2/**", "/login/oauth2/code/**").permitAll()
                        // 🔹 WebSocket 관련 요청 허용 (인증 없이 접근 가능)
                        .requestMatchers("/ws/**", "/api/member/reissue/access-token", "/api/member/logout").permitAll()
                        .requestMatchers("/oauth2/authorization/**").permitAll()
                        // 🔹 JWT 인증이 필요한 요청
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/", "/ws/**").permitAll() // API & WebSocket 요청 허용
                        // OAuth 요청 허용
                        .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                )

                .oauth2Login(oauth2 -> {
                            oauth2
                                    .userInfoEndpoint(userInfo -> userInfo.userService(
                                            customOAuth2UserService)) // .userService(new DefaultOAuth2UserService())
                                    .successHandler(successHandler)
                                    .failureHandler(failureHandler);
                        }
                )

                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable())

                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        })
                )

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

