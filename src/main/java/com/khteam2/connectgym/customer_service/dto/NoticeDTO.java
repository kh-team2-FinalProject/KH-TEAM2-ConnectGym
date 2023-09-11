package com.khteam2.connectgym.customer_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticeDTO {
    private Long no;
    private String title;
    private String content;
    private LocalDateTime noticeDatetime;
    private Integer topContent;
}
