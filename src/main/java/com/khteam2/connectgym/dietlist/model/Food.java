package com.khteam2.connectgym.dietlist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// Data 전달 용도

@Data
@Entity
<<<<<<< HEAD
=======
@Builder
@NoArgsConstructor
@AllArgsConstructor
>>>>>>> 309cc76f85971ff732e82cbc6b328b150e10c96d
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
    private Double sat_fat; // 포화 지방
    private Double trans_fat; // 트랜스 지방
    private Double kcal; // 칼로리
    private Double nat; // 나트륨
    private Double sugar;  // 당류
    private String animal_plant; // 가공업체


}
