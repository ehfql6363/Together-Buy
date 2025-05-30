package com.ssafy.TogetherBuyMain.member.dto.response;

import com.ssafy.TogetherBuyMain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDTO {

    private Long memberId;
    private String email;
    private String nickname;

    public static MemberResponseDTO of(Member member){
        return new MemberResponseDTO(member.getMemberId(), member.getEmail(), member.getNickname());
    }

}
