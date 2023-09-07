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
@Table(name="reviews")
public class Review {

    @Id
    @GeneratedValue
    private Long no;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_detail_no")
    private OrderDetail orderDetail;

    private int rating;
    private String reviewTitle;
    private String reviewContent;
    private LocalDateTime regDate;


}
