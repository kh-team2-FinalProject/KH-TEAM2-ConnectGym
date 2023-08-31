package com.khteam2.connectgym.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khteam2.connectgym.member.Member;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByMember(Member member);

    List<Order> findByMemberOrderByDayOfPaymentDesc(Member member);
    // @Query(value = "SELECT * FROM orders o" +
    //     " JOIN order_details od ON od.order_no = o.no" +
    //     " JOIN lesson l ON l.id = od.lesson_no"
    //     , nativeQuery = true)
    // List<Order> findOrderListByMemberNo();
}
