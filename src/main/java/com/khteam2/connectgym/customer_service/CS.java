package com.khteam2.connectgym.customer_service;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

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
    private LocalDateTime faq_datetime;
    @Column(name = "user_id", nullable = false)
    private String user_id;
    @Column(name = "category_id", nullable = false)
    private Long category_id;
}
