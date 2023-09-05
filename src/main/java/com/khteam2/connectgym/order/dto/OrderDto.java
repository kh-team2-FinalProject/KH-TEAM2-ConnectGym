package com.khteam2.connectgym.order.dto;

import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.order.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private String no;
    private Member member;
    private String type;
    private Timestamp dayOfPayment;
    private Long orderPay;

    public Order toEntity() {
        return Order.builder()
            .no(no)
            .member(member)
            .type(type)
            .dayOfPayment(dayOfPayment)
            .orderPay(orderPay)
            .build();
    }

    public static OrderDto of(Order order) {
        return OrderDto.builder()
            .no(order.getNo())
            .member(order.getMember())
            .type(order.getType())
            .dayOfPayment(order.getDayOfPayment())
            .orderPay(order.getOrderPay())
            .build();
    }
}
