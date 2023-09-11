package com.khteam2.connectgym.review.dto;

import com.khteam2.connectgym.order.OrderDetail;
import com.khteam2.connectgym.review.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDto {
    private OrderDetail orderDetail;
    private int rating;
    private String reviewTitle;
    private String reviewContent;

    public Review toEntity() {
        return Review.builder()
            .orderDetail(orderDetail)
            .rating(rating)
            .reviewTitle(reviewTitle)
            .reviewContent(reviewContent)
            .build();
    }
}
