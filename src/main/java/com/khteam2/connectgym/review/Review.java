package com.khteam2.connectgym.review;


import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


public class Review {

    @Id
    @GeneratedValue
    private Long reviewId;

    private Long reviewerId;



}
