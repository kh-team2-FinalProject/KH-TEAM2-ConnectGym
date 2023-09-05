package com.khteam2.connectgym.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProgressRepository extends JpaRepository<OrderProgress, Long> {
    OrderProgress findByOrderNo(String orderNo);
}
