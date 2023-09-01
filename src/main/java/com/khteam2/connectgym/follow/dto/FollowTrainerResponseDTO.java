package com.khteam2.connectgym.follow.dto;

import com.khteam2.connectgym.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FollowTrainerResponseDTO {

    private List<Member> followUserList;
    private int trainerFollowCnt;
    private boolean followStatus;

}
