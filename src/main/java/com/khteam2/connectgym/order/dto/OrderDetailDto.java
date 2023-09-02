package com.khteam2.connectgym.order.dto;

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
    private String orderNo;
    private Long lessonNo;
    private Long enrollKey;

    public OrderDetailDto(OrderDetail orderDetail) {
        this.no = orderDetail.getNo();
        if (orderDetail.getOrder() != null) {
            this.orderNo = orderDetail.getOrder().getNo();
        }
        if (orderDetail.getLesson() != null) {
            this.lessonNo = orderDetail.getLesson().getNo();
        }
        this.enrollKey = orderDetail.getEnrollKey();
    }
}
