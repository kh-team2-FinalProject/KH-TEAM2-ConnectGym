package com.khteam2.connectgym.order;

import com.khteam2.connectgym.lesson.Lesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@org.hibernate.annotations.Table(appliesTo = "order_detail", comment = "주문 상세 내역")
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
    @Column(nullable = false, unique = true)
    private Long enrollKey;
}
