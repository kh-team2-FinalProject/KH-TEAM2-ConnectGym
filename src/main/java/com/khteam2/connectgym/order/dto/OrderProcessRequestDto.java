package com.khteam2.connectgym.order.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class OrderProcessRequestDto {
    private String imp_uid;
    private String merchant_uid;
    private Boolean imp_success;
    private String error_code;
    private String error_msg;
}
