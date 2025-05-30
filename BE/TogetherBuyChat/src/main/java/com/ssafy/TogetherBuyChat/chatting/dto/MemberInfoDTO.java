package com.ssafy.TogetherBuyChat.chatting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoDTO {
    private Long memberId;
    private String nickname;
    private String imageUrl;
}