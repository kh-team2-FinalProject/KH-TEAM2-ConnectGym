package com.khteam2.connectgym.customer_service;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "notice_datetime")
    @CreationTimestamp
    private LocalDateTime noticeDatetime;
    @Column(name = "top_con", nullable = false)
    private Integer topContent;
}
