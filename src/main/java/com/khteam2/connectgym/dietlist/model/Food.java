package com.khteam2.connectgym.dietlist.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Data 전달 용도

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodCd; // 식품 번호

    private String foodNm; //식품명
    private Double foodSize; // 1회 제공량
    private Double choc; //탄수화물
    private Double prot; // 단백질
    private Double fat; //지방
    private Double satFat; // 포화 지방
    private Double transFat; // 트랜스 지방
    private Double kcal; // 칼로리
    private Double nat; // 나트륨
    private Double sugar; // 당류
    private String animalPlant; // 가공업체

}
