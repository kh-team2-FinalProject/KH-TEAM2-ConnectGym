package com.khteam2.connectgym.order;

import com.khteam2.connectgym.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    private String no;
    @JoinColumn(name = "member_no", nullable = false)
    @ManyToOne
    private Member member;
    @Column(nullable = false, length = 50)
    private String type;
    @Column(name = "day_of_payment", nullable = false)
    @Comment("주문한 날짜 및 시간")
    private Timestamp dayOfPayment;
    @Column(name = "order_pay", nullable = false)
    private Long orderPay;
}
