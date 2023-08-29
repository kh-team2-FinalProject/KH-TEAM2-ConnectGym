package com.khteam2.connectgym.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OrderListOrderDto {
    private String orderNo;
    private LocalDateTime orderDate;
    private long totalPrice;
    private List<OrderListOrderDetailDto> detailDtoList;
}
