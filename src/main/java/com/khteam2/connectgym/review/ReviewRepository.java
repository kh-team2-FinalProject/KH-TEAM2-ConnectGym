package com.khteam2.connectgym.review;

import com.khteam2.connectgym.review.dto.RatingCountDto;
import com.khteam2.connectgym.review.dto.ReviewResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 리뷰 작성 여부
    @Query("SELECT r FROM Review r WHERE r.orderDetail.no = ?1")
    Optional<Review> findOrderDetailNo(Long no);

    // 회원별 리뷰
    @Query("SELECT r FROM Review r WHERE r.orderDetail.order.member.no = ?1")
    List<Review> findByMemberNo(Long userNo);

    // 트레이너별 리뷰 개수
    @Query("SELECT COUNT(r) FROM Review r "
        + "JOIN r.orderDetail od "
        + "JOIN od.lesson l "
        + "JOIN l.trainer t "
        + "WHERE t.no = ?1")
    int findCountByTrainerNo(Long trainerNo);

    // 트레이너별 리뷰(+멤버 ID 를 위해 DTO로 반환)
    @Query("SELECT NEW com.khteam2.connectgym.review.dto.ReviewResponseDto(" +
        "r.no, m.userId, r.rating, r.reviewTitle, r.reviewContent, r.regDate" +
        ") " +
        "FROM Review r " +
        "JOIN r.orderDetail od " +
        "JOIN od.order.member m " +
        "JOIN od.lesson l " +
        "JOIN l.trainer t " +
        "WHERE t.no = ?1 " +
        "ORDER BY r.regDate DESC")
    List<ReviewResponseDto> findTrainerReviewsByTrainerNo(Long trainerNo);

    // 트레이너별 리뷰 평균
    @Query("SELECT AVG(r.rating) FROM Review r "
        + "JOIN r.orderDetail od "
        + "JOIN od.lesson l "
        + "JOIN l.trainer t "
        + "WHERE t.no = ?1")
    Optional<Double> findAvgRatingByTrainerNo(Long trainerNo);

    // 별점 카운드
    @Query("SELECT NEW com.khteam2.connectgym.review.dto.RatingCountDto( "
        + "COALESCE(SUM(CASE WHEN r.rating = 1 THEN 1 ELSE 0 END), 0), "
        + "COALESCE(SUM(CASE WHEN r.rating = 2 THEN 1 ELSE 0 END), 0), "
        + "COALESCE(SUM(CASE WHEN r.rating = 3 THEN 1 ELSE 0 END), 0), "
        + "COALESCE(SUM(CASE WHEN r.rating = 4 THEN 1 ELSE 0 END), 0), "
        + "COALESCE(SUM(CASE WHEN r.rating = 5 THEN 1 ELSE 0 END), 0)"
        + ") "
        + "FROM Review r "
        + "WHERE r.orderDetail.lesson.trainer.no = ?1")
    RatingCountDto findRatingCountsByTrainerNo(Long trainerNo);

    // 메인 TOP3 리뷰 출력(+멤버 ID 를 위해 DTO로 반환)
    @Query("SELECT NEW com.khteam2.connectgym.review.dto.ReviewResponseDto(" +
        "r.no, m.userId, r.rating, r.reviewTitle, r.reviewContent, r.regDate" +
        ") " +
        "FROM Review r " +
        "JOIN r.orderDetail od " +
        "JOIN od.order.member m " +
        "JOIN od.lesson l " +
        "JOIN l.trainer t " +
        "ORDER BY r.rating DESC")
    List<ReviewResponseDto> findReviewOrderByRating();
}
