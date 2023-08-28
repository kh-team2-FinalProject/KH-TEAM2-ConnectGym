package com.khteam2.connectgym.order;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    @ManyToOne
    private Order order;
    @JoinColumn(name = "lesson_no", nullable = false)
    @ManyToOne
    private Lesson lesson;
    @Column(name = "enroll_key", nullable = false)
    private Long enrollKey;
}
