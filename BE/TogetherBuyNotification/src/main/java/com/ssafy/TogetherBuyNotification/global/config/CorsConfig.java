package com.ssafy.TogetherBuyNotification.global.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // 허용할 Origin (프론트엔드 주소)
        // 보안을 위해 실제 배포 도메인을 넣으셔야 합니다.
        config.setAllowedOrigins(List.of("http://localhost:3000"));

        // HTTP Method
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // 헤더 허용
        config.setAllowedHeaders(List.of("*"));

        // 자격증명 허용 (쿠키 등)
        config.setAllowCredentials(true);

        // 만약 SameSite 설정이 필요하면 Response Header에서 직접 처리하거나,
        // setAllowedOrigins 대신 setAllowCredentials(true) + setAllowedOriginPatterns("*") 등으로 조합해야 함

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
