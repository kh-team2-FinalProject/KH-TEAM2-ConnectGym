package com.khteam2.connectgym.trainer.dto;

import com.khteam2.connectgym.member.dto.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerEnterRoomResponseDto {
    private String titleCode;
    private Map<Long, MemberResponseDto> memberMap;
    private String errorMsg = "Success";
}
