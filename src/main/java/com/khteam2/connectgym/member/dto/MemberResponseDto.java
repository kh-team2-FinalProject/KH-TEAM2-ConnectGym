package com.khteam2.connectgym.member.dto;

import com.khteam2.connectgym.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseDto {
    private Long no;
    private String userId;
    private String userPw;
    private String userName;
    private String userTel;
    private String userEmail;
    private String userAddress;

    public MemberResponseDto(Member entity) {
        if (entity != null) {
            this.no = entity.getNo();
            this.userId = entity.getUserId();
            this.userPw = entity.getUserPw();
            this.userName = entity.getUserName();
            this.userTel = entity.getUserTel();
            this.userEmail = entity.getUserEmail();
            this.userAddress = entity.getUserAddress();
        }
    }
}
