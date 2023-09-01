package com.khteam2.connectgym.member.dto;

import com.khteam2.connectgym.member.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class MemberResponseDTO {
    private Long no;
    private String userId;
    private String userPw;
    private String userName;
    private String userTel;
    private String userEmail;
    private String userAddress;

    public MemberResponseDTO(Member entity) {
        this.no = entity.getNo();
        this.userId = entity.getUserId();
        this.userPw = entity.getUserPw();
        this.userName = entity.getUserName();
        this.userTel = entity.getUserTel();
        this.userEmail = entity.getUserEmail();
        this.userAddress = entity.getUserAddress();
    }

}
