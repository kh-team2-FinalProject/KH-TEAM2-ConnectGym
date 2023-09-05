package com.khteam2.connectgym.order.dto;

import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.order.Order;
import com.khteam2.connectgym.order.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailDto {
    private Long no;
    private Order order;
    private Lesson lesson;
    private Long enrollKey;

    public OrderDetail toEntity() {
        return OrderDetail.builder()
            .no(no)
            .order(order)
            .lesson(lesson)
            .enrollKey(enrollKey)
            .build();
    }

    public static OrderDetailDto of(OrderDetail orderDetail) {
        return OrderDetailDto.builder()
            .no(orderDetail.getNo())
            .order(orderDetail.getOrder())
            .lesson(orderDetail.getLesson())
            .enrollKey(orderDetail.getEnrollKey())
            .build();
    }
}
