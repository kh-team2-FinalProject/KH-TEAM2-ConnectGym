package com.khteam2.connectgym.order.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class OrderProcessResponseDto {
    private boolean success;
    private String message;
    private String url;
}
