package com.khteam2.connectgym.customer_service;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "customer_service")
public class CS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @CreationTimestamp
    private LocalDateTime faqDatetime;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private Long categoryId;
}
