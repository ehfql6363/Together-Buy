package com.ssafy.TogetherBuyMain.member.entity;

import com.ssafy.TogetherBuyMain.global.util.AddressUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;

@Getter
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sido;
    private String sigungu;
    private String eupmyeondong;
    private String ri;
    private String roadName; // 도로명 주소
    private String roadNumber;  // 건물 번호
    private String detailAddress;  // 상세 주소

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateLocationFromAddress(Matcher matcher) {
        System.out.println("matcherAddress = " + matcher.group(0));
        this.sido = matcher.group(1);
        this.sigungu = matcher.group(2);
        this.eupmyeondong = matcher.group(3);
        this.ri = matcher.group(4);
        this.roadName = matcher.group(5);
        this.roadNumber = matcher.group(6);
        this.detailAddress = matcher.group(7);
    }


    public String getLocation() {
        return this.sido + " " + this.sigungu + " " + this.eupmyeondong;
    }

    public String fullAddress() {
        return String.join(" ",
                (sido != null ? sido : ""),
                (sigungu != null ? sigungu : ""),
                (eupmyeondong != null ? eupmyeondong : ""),
                (ri != null ? ri : ""),
                (roadName != null ? roadName : ""),
                (roadNumber != null ? roadNumber : ""),
                (detailAddress != null ? detailAddress : "")
        ).trim().replaceAll(" +", " ");
    }
}

