package com.khteam2.connectgym.order.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProcessDto {
    private String sMerchantUid;
    private Long sTotalPrice;
    private Long sLoginMemberNo;
    private List<Long> sLessonNolist;
    private boolean isApi;
    private String imp_uid;
    private String merchant_uid;
    private Boolean imp_success;
    private String error_code;
    private String error_msg;
}
