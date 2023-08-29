package com.khteam2.connectgym.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderListDto {
    private String status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String q;
}
