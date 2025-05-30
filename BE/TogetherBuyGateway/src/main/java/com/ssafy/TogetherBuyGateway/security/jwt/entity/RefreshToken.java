package com.ssafy.TogetherBuyGateway.security.jwt.entity;

import com.ssafy.TogetherBuyGateway.member.entity.Member;
import com.ssafy.TogetherBuyGateway.member.entity.Provider;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
