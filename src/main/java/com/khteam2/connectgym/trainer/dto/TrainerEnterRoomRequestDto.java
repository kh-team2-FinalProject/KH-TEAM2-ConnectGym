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
public class TrainerEnterRoomRequestDto {

    private String titleCode;
    private Long enrollKey;

}
