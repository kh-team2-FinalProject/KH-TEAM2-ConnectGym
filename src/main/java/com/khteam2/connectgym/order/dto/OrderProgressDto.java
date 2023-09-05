package com.khteam2.connectgym.order.dto;

import com.khteam2.connectgym.order.OrderProgress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProgressDto {
    private Long no;
    private String orderNo;
    private Long price;

    public OrderProgress toEntity() {
        return OrderProgress.builder()
            .no(no)
            .orderNo(orderNo)
            .price(price)
            .build();
    }

    public static OrderProgressDto of(OrderProgress orderProgress) {
        return OrderProgressDto.builder()
            .no(orderProgress.getNo())
            .orderNo(orderProgress.getOrderNo())
            .price(orderProgress.getPrice())
            .build();
    }
}
