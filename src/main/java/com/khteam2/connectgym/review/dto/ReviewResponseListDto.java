package com.khteam2.connectgym.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponseListDto {
    private List<ReviewResponseDto> trainerReviewResponseDtoList;
    private double ratingAvg;
    private RatingCountDto ratingCountDto;
}
