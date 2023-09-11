package com.khteam2.connectgym.trainer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerEnterRoomRequestDto {
    private String titleCode;
    private Long enrollKey;
}
