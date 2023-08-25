package com.khteam2.connectgym.member.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDTO {

    private Long no;
    private String userId;
    private String userPw;
    private String userName;
    private String userTel;
    private String userEmail;
    private String userAddress;
    private LocalDateTime regDate;


}
