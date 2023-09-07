package com.khteam2.connectgym.review;

import com.khteam2.connectgym.review.dto.ReviewRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequiredArgsConstructor
public class ReviewApiController {
    private final ReviewService reviewService;

    //리뷰 저장
    @PostMapping("/review/createReview")
    public String createReview(ReviewRequestDto dto,
                               @RequestParam("profileImgFile") MultipartFile profileImgFile) {

        reviewService.createReview(dto);

        return "/mypage/myReviewList";

    }


}

