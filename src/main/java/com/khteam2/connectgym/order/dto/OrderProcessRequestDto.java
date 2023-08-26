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
public class OrderProcessRequestDto {
    private String sMerchantUid;
    private Long sTotalPrice;
    private Long sLoginMemberNo;
    private List<Long> sLessonNolist;
    private boolean isApi;
    private String impUid;
    private String merchantUid;
    private Boolean impSuccess;
    private String errorCode;
    private String errorMsg;
}
