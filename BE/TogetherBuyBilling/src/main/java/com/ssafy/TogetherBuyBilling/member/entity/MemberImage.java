package com.ssafy.TogetherBuyBilling.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String fileName;
    private String contentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateImage(String url, String fileName, String contentType) {
        this.url = url;
        this.fileName = fileName;
        this.contentType = contentType;
    }

    public String getImageUrl() {
        return this.url;
    }
}
