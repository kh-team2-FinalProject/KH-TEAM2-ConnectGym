package com.khteam2.connectgym.customer_service.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CSDTO {

    private Long no;
    private String title;
    private String content;
    private LocalDateTime faq_datetime;
    private String user_id;
    private Long category_id;
}
