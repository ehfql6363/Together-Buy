package com.ssafy.TogetherBuyBilling.shop.entity.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.StringJoiner;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long meetingLocationId;

    private String sido;
    private String sigungu;
    private String eupmyeondong;
    private String ri;
    private String loadName;
    private String loadNumber;

    @OneToOne(mappedBy = "meetingLocation", fetch = FetchType.LAZY)
    private Form form;

    public void updateLocation(String sido, String sigungu, String eupmyeondong, String ri, String loadName, String loadNumber) {
        this.sido = sido;
        this.sigungu = sigungu;
        this.eupmyeondong = eupmyeondong;
        this.ri = ri;
        this.loadName = loadName;
        this.loadNumber = loadNumber;
    }

    public String buildFullAddress() {
        StringJoiner joiner = new StringJoiner(" ");
        if (sido != null) joiner.add(sido);
        if (sigungu != null) joiner.add(sigungu);
        if (eupmyeondong != null) joiner.add(eupmyeondong);
        if (ri != null) joiner.add(ri);
        if (loadName != null) joiner.add(loadName);
        if (loadNumber != null) joiner.add(loadNumber);
        return joiner.toString();
    }
}
