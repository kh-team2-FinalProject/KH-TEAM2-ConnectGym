package com.khteam2.connectgym.follow.dto;

import com.khteam2.connectgym.trainer.dto.TrainerResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowForUserDto {
    private List<TrainerResponseDto> followTrainerList;
    private int trainerFollowCnt;
    private boolean followStatus;
    private String keyword; // 검색용
}
