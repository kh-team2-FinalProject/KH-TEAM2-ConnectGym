package com.khteam2.connectgym.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.orderDetail.order.member.no =?1")
    List<Review> findByMemberNo (Long userNo);

    @Query("SELECT r FROM Review r WHERE r.orderDetail.no = ?1")
    Optional<Review> findOrderDetailNo(Long no);
}
