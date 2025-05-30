package com.ssafy.TogetherBuyNotification.shop.entity.common;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class MeetingLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long meetingLocationId;

    private String meetingAddress;
    private Double latitude;
    private Double longitude;

    @OneToOne(mappedBy = "meetingLocation", fetch = FetchType.LAZY)
    private Form form;

    public void updateAddress(String meetingAddress) {
        this.meetingAddress = meetingAddress;
    }

    public void updateCoordinate(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
