package com.khteam2.connectgym.review;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor@AllArgsConstructor@Builder
@Entity
public class Review {

    @Id
    @GeneratedValue
    private Long reviewId;

    private Long reviewerId;



}
