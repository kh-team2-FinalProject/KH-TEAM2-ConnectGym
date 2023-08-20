package com.khteam2.connectgym.order;

public interface OrderRepository { // extends JpaRepository<Order, Long>
    // @Query(value = "SELECT MAX(no) FROM order", nativeQuery = true)
    Long getMaxNo();
}
