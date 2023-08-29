package com.khteam2.connectgym.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class OrderListDetailDto {
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long price;
    private String status;
}
