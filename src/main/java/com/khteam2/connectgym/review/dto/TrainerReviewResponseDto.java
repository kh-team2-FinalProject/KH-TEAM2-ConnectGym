package com.khteam2.connectgym.review.dto;

import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.order.OrderDetail;
import com.khteam2.connectgym.trainer.Trainer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonReviewResponseDto {

    private Long no;
    private String userId;
    private String trainerNo;
    private int rating;
    private String reviewTitle;
    private String reviewContent;
    private LocalDateTime regDate;
}
