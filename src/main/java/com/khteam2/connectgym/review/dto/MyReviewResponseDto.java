package com.khteam2.connectgym.review.dto;

import com.khteam2.connectgym.review.ReviewImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyReviewResponseDto {
    private Long no;
    private Long trainerNo;
    private String trainerName;
    private int rating;
    private String reviewTitle;
    private String reviewContent;
    private LocalDateTime regDate;
    private List<ReviewImg> reviewImgList;
}
