package com.khteam2.connectgym.order.dto;

import com.khteam2.connectgym.common.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OrderListResponseDto {
    private boolean success;
    private String message;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private String search;
    private List<OrderListOrderDto> orderListOrderDtoList;
    private Pagination pagination;
}
