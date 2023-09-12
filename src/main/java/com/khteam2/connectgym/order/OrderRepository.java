package com.khteam2.connectgym.order;

import com.khteam2.connectgym.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {
    @Query("SELECT o FROM Order o WHERE o.no = ?1")
    Optional<Order> findByOrderNo(String OrderNo);

    List<Order> findByMember(Member member);

    Page<Order> findByMemberOrderByDayOfPaymentDesc(Member member, Pageable pageable);

    @Query("SELECT o FROM Order o"
        + " WHERE o.member = :member"
        + " AND (o.dayOfPayment BETWEEN :startDate AND CONCAT(:endDate, ' 23:59:59'))"
        + " ORDER BY o.dayOfPayment DESC")
    Page<Order> findByMemberDayOfPaymentBetween(
        @Param("member") Member member,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate,
        Pageable pageable
    );
}
