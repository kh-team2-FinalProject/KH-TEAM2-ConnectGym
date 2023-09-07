package com.khteam2.connectgym.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerReviewResponseDto {

    private Long no;
    private String userId;
    private String trainerNo;
    private int rating;
    private String reviewTitle;
    private String reviewContent;
    private LocalDateTime regDate;
}
