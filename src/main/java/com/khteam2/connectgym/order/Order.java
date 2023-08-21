package com.khteam2.connectgym.order;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Comment;

import com.khteam2.connectgym.member.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
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
