package com.khteam2.connectgym.member.dto;

import com.khteam2.connectgym.member.MemberClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginRequestDto {
    private String id;
    private String password;
    private MemberClass memberClass;
}
