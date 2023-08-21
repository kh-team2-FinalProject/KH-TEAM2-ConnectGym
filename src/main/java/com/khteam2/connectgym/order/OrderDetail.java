package com.khteam2.connectgym.order;

import com.khteam2.connectgym.lesson.Lesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
}
