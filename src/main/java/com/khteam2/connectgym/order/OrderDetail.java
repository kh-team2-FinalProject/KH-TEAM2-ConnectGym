package com.khteam2.connectgym.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.khteam2.connectgym.lesson.Lesson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @JoinColumn(name = "order_no", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    @JoinColumn(name = "lesson_no", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Lesson lesson;
    @Column(nullable = false)
    private Long enrollKey;
}
