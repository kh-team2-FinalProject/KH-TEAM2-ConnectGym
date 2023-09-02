package com.khteam2.connectgym.follow.dto;

import com.khteam2.connectgym.member.dto.MemberResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FollowForTrainerResponseDTO {

    private List<MemberResponseDTO> followUserList;
    private int trainerFollowCnt;
    private boolean followStatus;


}
