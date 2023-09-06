package com.khteam2.connectgym.customer_service.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NoticeDTO {

    private Long no;
    private String title;
    private String content;
    private LocalDateTime noticeDatetime;
    private Integer topContent;

}
