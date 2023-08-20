package com.khteam2.connectgym.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @Column(name = "user_no", nullable = false)
    private Long userNo;
    @Column(nullable = false, length = 50)
    private String type;
    @Column(name = "day_of_payment", nullable = false)
    @Comment("주문한 날짜 및 시간")
    private Timestamp dayOfPayment;
    @Column(name = "order_pay", nullable = false)
    private Long orderPay;
}
