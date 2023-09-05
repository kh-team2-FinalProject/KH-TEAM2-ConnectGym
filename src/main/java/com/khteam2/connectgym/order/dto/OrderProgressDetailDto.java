package com.khteam2.connectgym.order.dto;

import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.order.OrderProgress;
import com.khteam2.connectgym.order.OrderProgressDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProgressDetailDto {
    private Long no;
    private OrderProgress orderProgress;
    private Lesson lesson;

    public OrderProgressDetail toEntity() {
        return OrderProgressDetail.builder()
            .no(no)
            .orderProgress(orderProgress)
            .lesson(lesson)
            .build();
    }

    public static OrderProgressDetailDto of(OrderProgressDetail orderProgressDetail) {
        return OrderProgressDetailDto.builder()
            .no(orderProgressDetail.getNo())
            .orderProgress(orderProgressDetail.getOrderProgress())
            .lesson(orderProgressDetail.getLesson())
            .build();
    }
}
