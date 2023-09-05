package com.khteam2.connectgym.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Entity
@org.hibernate.annotations.Table(appliesTo = "order_progress", comment = "주문할 때 사용되는 테이블")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OrderProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @Column(unique = true, nullable = false)
    @Comment(value = "주문 번호")
    private String orderNo;
    @Column(nullable = false)
    @Comment("결제 예정 금액")
    private Long price;
}
