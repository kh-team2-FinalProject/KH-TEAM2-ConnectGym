package com.khteam2.connectgym.member.dto;

import com.khteam2.connectgym.member.MemberClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MemberLoginResponseDto {
    private boolean success;
    private String message;
    private Long memberNo;
    private MemberClass memberClass;
}
