package com.ssafy.TogetherBuyMain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberInitializeDTO {
    private String email;
    private String password;
    private String nickname;
}
