package com.khteam2.connectgym.order.dto;

import com.khteam2.connectgym.order.Order;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class OrderCompleteResponseDto {
    private boolean success;
    private List<Order> orderList;
    private String url;
}
