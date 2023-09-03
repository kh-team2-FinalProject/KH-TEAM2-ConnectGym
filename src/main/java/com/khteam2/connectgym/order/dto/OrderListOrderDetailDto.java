package com.khteam2.connectgym.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class OrderListOrderDetailDto {
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long price;
    private String status;
    private String trainerName;
    private String imageUrl;
    private Long lessonNo;
}
