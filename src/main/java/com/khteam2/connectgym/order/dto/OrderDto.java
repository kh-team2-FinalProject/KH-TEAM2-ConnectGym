package com.khteam2.connectgym.order.dto;

import com.khteam2.connectgym.order.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private String no;
    private Long memberNo;
    private String type;
    private Instant dayOfPayment;
    private Long orderPay;

    public OrderDto(Order order) {
        this.no = order.getNo();
        if (order.getMember() != null) {
            this.memberNo = order.getMember().getNo();
        }
        this.type = order.getType();
        this.dayOfPayment = order.getDayOfPayment().toInstant();
        this.orderPay = order.getOrderPay();
    }
}
