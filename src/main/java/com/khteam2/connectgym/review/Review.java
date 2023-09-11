package com.khteam2.connectgym.review;

import com.khteam2.connectgym.order.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"order_detail_no"})}
)
public class Review {
    @Id
    @GeneratedValue
    private Long no;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_detail_no", unique = true)
    private OrderDetail orderDetail;

    private int rating;
    private String reviewTitle;
    private String reviewContent;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @PrePersist
    protected void onCreate() {
        regDate = LocalDateTime.now();
    }
}
