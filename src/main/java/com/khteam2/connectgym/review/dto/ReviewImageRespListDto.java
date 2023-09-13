package com.khteam2.connectgym.review.dto;

import com.khteam2.connectgym.review.ReviewImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewImageListDto {

    ReviewResponseListDto reviewResponseListDto;
    Map<Long, List<ReviewImg>> reviewImgMap;
}
