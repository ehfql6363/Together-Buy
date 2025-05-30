package com.ssafy.TogetherBuyChat.security.jwt.entity;

import com.ssafy.TogetherBuyChat.member.entity.Member;
import com.ssafy.TogetherBuyChat.member.entity.Provider;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated
    private Provider provider;

    private String providerId;

    @Column(nullable = false)
    private String refreshToken;

    private Long expiry;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    // Refresh Token 값 업데이트
    public void updateRefreshToken(String refreshToken, Long expiry) {
        this.refreshToken = refreshToken;
        this.expiry = expiry;
    }

    // 만료 여부 확인
    public boolean isExpired() {
        return System.currentTimeMillis() > expiry;
    }

    public void registerMember(Member member) {
        this.member = member;
    }

}
