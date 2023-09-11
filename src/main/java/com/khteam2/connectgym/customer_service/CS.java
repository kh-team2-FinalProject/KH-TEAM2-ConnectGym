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
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "faq_datetime")
    @CreationTimestamp
    private LocalDateTime faqDatetime;
    @Column(name = "user_id", nullable = false)
    private String userId;
    @Column(name = "category_id", nullable = false)
    private Long categoryId;
}
