package com.khteam2.connectgym.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class RatingCountDto {
    private Long rating1Count;
    private Long rating2Count;
    private Long rating3Count;
    private Long rating4Count;
    private Long rating5Count;


}
