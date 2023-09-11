package com.khteam2.connectgym.customer_service;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cs_category")
public class CS_Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String ctgy;
}
