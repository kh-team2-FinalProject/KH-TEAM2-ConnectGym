package com.khteam2.connectgym.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
    // @Query(value = "SELECT * FROM orders o" +
    //     " JOIN order_details od ON od.order_no = o.no" +
    //     " JOIN lesson l ON l.id = od.lesson_no"
    //     , nativeQuery = true)
    // List<Order> findOrderListByMemberNo();
}
