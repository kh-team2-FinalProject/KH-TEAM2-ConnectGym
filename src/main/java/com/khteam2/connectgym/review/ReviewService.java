package com.khteam2.connectgym.review;

import com.khteam2.connectgym.order.OrderDetailRepository;
import com.khteam2.connectgym.review.dto.*;
import com.khteam2.connectgym.upload.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImgRepository reviewImgRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final S3Uploader s3Uploader;

    //리뷰 저장
    @Transactional
    public Long createReview(Long userNo, Long orderDetailNo,
                             ReviewRequestDto dto, MultipartFile[] reviewImgs) {
        dto.setOrderDetail(orderDetailRepository.findById(orderDetailNo).orElse(null));

        Review review = dto.toEntity();
        Review getReview = reviewRepository.save(review);

        //리뷰 사진
        try {
            for (MultipartFile file : reviewImgs) {
                if (!file.isEmpty()) {
                    String reviewImgUrl = s3Uploader.uploadReviewFile(file, userNo);
                    ReviewImg reviewImg = ReviewImg.builder()
                        .reviewImg(reviewImgUrl)
                        .review(getReview)
                        .build();
                    reviewImgRepository.save(reviewImg);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return getReview.getNo();
    }


    //나의 리뷰
    public List<MyReviewResponseDto> myReview(Long userNo) {
        List<MyReviewResponseDto> reviewList = new ArrayList<>();

        List<Review> reviews = reviewRepository.findByMemberNo(userNo);

        if (reviews.size() > 0) {
            for (Review val : reviews) {

                MyReviewResponseDto myReviewResponseDto = MyReviewResponseDto.builder()
                    .no(val.getNo())
                    .trainerNo(val.getOrderDetail().getLesson().getTrainer().getNo())
                    .trainerName(val.getOrderDetail().getLesson().getTrainer().getTrainerName())
                    .rating(val.getRating())
                    .reviewTitle(val.getReviewTitle())
                    .reviewContent(val.getReviewContent())
                    .regDate(val.getRegDate())
                    .reviewImgList(reviewImgRepository.findAllByReviewNo(val.getNo()))
                    .build();
                reviewList.add(myReviewResponseDto);
            }
        }

        return reviewList;
    }

    @Modifying
    @Transactional
    public void deleteReview(Long reviewNo) {
        reviewRepository.deleteById(reviewNo);
    }

    public int trainerReviewCount(Long trainerNo) {
        return reviewRepository.findCountByTrainerNo(trainerNo);
    }

    //트레이너별 리뷰(repository 반환타입 Dto)
    public ReviewResponseListDto trainerReview(Long trainerNo) {
        List<ReviewResponseDto> dtoList = reviewRepository.findTrainerReviewsByTrainerNo(trainerNo);
        double ratingAvg = reviewRepository.findAvgRatingByTrainerNo(trainerNo).orElse(0.0);
        double ratingAvgDecimal = Math.round(ratingAvg * 10.0) / 10.0;
        RatingCountDto ratingCountDto = reviewRepository.findRatingCountsByTrainerNo(trainerNo);

        ReviewResponseListDto listDto = ReviewResponseListDto.builder()
            .trainerReviewResponseDtoList(dtoList)
            .ratingAvg(ratingAvgDecimal)
            .ratingCountDto(ratingCountDto)
            .build();
        return listDto;
    }

    //메인 메뉴 TOP3 리뷰(repository 반환타입 Dto)
    public List<ReviewResponseDto> top3Review() {
        return reviewRepository.findReviewOrderByRating();
    }
}
