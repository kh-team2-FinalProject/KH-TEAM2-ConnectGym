package com.khteam2.connectgym.review;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.review.dto.ReviewRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    //리뷰 저장
    @PostMapping("/review/createReview")
    public String createReview(Model model, ReviewRequestDto dto,
                               @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long userNo,
                               @RequestParam("orderDetailNo") Long orderDetailNo,
                               @RequestParam("reviewImg") MultipartFile[] reviewImgs) {
        reviewService.createReview(userNo, orderDetailNo,dto, reviewImgs);

        return "redirect:/mypage/myReviewList";

    }

    //리뷰 삭제
    @ResponseBody
    @DeleteMapping("/review/del/{reviewNo}")
    public void deleteReview(@PathVariable Long reviewNo){
        reviewService.deleteReview(reviewNo);
    }


    //유저별 리뷰리스트(->MemberController)
    //레슨별 리뷰리스트


}

