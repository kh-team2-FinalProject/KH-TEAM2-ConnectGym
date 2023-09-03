package com.khteam2.connectgym.order;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.khteam2.connectgym.member.Member;

public interface OrderRepository extends JpaRepository<Order, String> {

    @Query("select o from Order o where o.no=?1")
    Optional<Order> findByOrderNo(String OrderNo);

    List<Order> findByMember(Member member);

    List<Order> findByMemberOrderByDayOfPaymentDesc(Member member);

    @Query(value = "SELECT o FROM Order o WHERE o.member = :member"
            + " AND (o.dayOfPayment BETWEEN :startDate AND CONCAT(:endDate, ' 23:59:59'))"
            + " ORDER BY o.dayOfPayment DESC")
    List<Order> findByMemberAndDayOfPaymentBetweenOrderByDayOfPaymentDesc(@Param("member") Member member,
            @Param("startDate") Date startDate, @Param("endDate") Date endDate);


    // @Query(value = "SELECT * FROM orders o" +
    // " JOIN order_details od ON od.order_no = o.no" +
    // " JOIN lesson l ON l.id = od.lesson_no"
    // , nativeQuery = true)
    // List<Order> findOrderListByMemberNo();
}
