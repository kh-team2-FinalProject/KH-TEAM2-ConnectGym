package com.khteam2.connectgym.trainer.dto;

import com.khteam2.connectgym.member.dto.MemberResponseDTO;
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
    private Map<Long,MemberResponseDTO> memberMap;
    private String errorMsg = "Success";

}
