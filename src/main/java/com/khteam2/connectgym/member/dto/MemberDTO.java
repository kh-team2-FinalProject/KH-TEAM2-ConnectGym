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

    private String provider; //어디 api에서 왔는가 ex)구글 네이버등등
    private String providerId;//api 생성고유번호


}
