package com.khteam2.connectgym.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OrderListResponseDto {
    private boolean success;
    private String message;
    private String orderNo;
    private LocalDateTime orderDate;
    private long totalPrice;
    private List<OrderListDetailDto> detailDtoList;
}
