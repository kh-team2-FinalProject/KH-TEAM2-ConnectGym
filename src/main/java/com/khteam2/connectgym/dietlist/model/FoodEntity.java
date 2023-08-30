package com.khteam2.connectgym.dietlist.model;


import lombok.*;

import javax.persistence.*;

// Data 매핑하는 용도

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name= "foods")
public class FoodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    private Long foodCd; // 식품 번호

    String foodNm;
    Double foodSize;
    Double choc;
    Double prot;
    Double fat;
    Double sat_fat;
    Double trans_fat;
    Double kcal;
    Double nat;
    Double sugar;
    String animal_plant;
}
