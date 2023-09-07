package com.khteam2.connectgym.review;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="review_imgs")
public class ReviewImg {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne
    @JoinColumn(name="review_no")
    private Review review;

    private String reviewImg;
}
