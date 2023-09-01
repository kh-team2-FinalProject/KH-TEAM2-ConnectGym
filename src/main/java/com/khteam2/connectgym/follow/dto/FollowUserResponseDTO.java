package com.khteam2.connectgym.follow.dto;

import com.khteam2.connectgym.trainer.Trainer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowUserResponseDTO {

    private List<Trainer> followTrainerList;
    private int trainerFollowCnt;
    private boolean followStatus;

}
