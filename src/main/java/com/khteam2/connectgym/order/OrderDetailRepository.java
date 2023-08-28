package com.khteam2.connectgym.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrder(Order order);

    OrderDetail findByEnrollKey(Long enrollKey);
}
