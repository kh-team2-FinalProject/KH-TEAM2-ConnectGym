package com.khteam2.connectgym.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT MAX(no) FROM order", nativeQuery = true)
    Long getMaxNo();

    // @Query(value = "SELECT * FROM orders o" +
    //     " JOIN order_details od ON od.order_no = o.no" +
    //     " JOIN lesson l ON l.id = od.lesson_no"
    //     , nativeQuery = true)
    // List<Order> findOrderListByMemberNo();
}
