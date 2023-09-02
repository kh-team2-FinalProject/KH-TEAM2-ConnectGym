package com.khteam2.connectgym.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrder(Order order);

    @Query("select o from OrderDetail o where o.order=?1")
    OrderDetail findByEnroll(Order order);

    List<OrderDetail> findByOrderOrderByNoDesc(Order order);

    OrderDetail findByEnrollKey(Long enrollKey);

    @Query(nativeQuery = true, value = "SELECT * FROM order_detail od"
        + " JOIN orders o ON o.no = od.order_no"
        + " JOIN users u ON u.no = o.member_no"
        + " WHERE u.no = :memberNo")
    List<OrderDetail> findByMemberNo(@Param(value = "memberNo") Long memberNo);
}
