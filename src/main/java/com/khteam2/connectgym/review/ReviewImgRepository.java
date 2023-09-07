package com.khteam2.connectgym.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewImgorRepository extends JpaRepository<ReviewImg, Long> {

    @Query("SELECT r FROM ReviewImg r WHERE r.review.member.no = ?1")
    List<ReviewImg> findReviewImgByMemberNo(Long memberNo);
}

