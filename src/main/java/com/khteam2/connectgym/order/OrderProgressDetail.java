package com.khteam2.connectgym.order;

import com.khteam2.connectgym.lesson.Lesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Entity
@Table(name = "order_progress_details")
@org.hibernate.annotations.Table(appliesTo = "order_progress_details", comment = "주문할 때 사용하는 정보 중 상세 정보를 담는 테이블")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderProgressDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @ManyToOne
    @JoinColumn(name = "order_progress_no", nullable = false)
    @Comment("결제 예정 테이블 번호")
    private OrderProgress orderProgress;
    @ManyToOne
    @JoinColumn(name = "lesson_no", nullable = false)
    @Comment("레슨 번호")
    private Lesson lesson;
}
