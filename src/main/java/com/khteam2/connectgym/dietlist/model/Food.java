

package com.khteam2.connectgym.dietlist.model;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// Data 전달 용도

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Food {

    private Long foodCd; // 식품 번호
    private String foodNm; //식품명
    private double foodSize;  // 1회 제공량
    private double choc; //탄수화물
    private double prot; // 단백질
    private double fat; //지방
    private double sat_fat; // 포화 지방
    private double trans_fat; // 트랜스 지방
    private double kcal; // 칼로리
    private double nat; // 나트륨
    private double sugar;  // 당류
    private String animal_plant; // 가공업체



    public FoodEntity toEntity() {
        return FoodEntity.builder()
            .foodCd(foodCd)
            .foodNm(foodNm)
            .choc(choc)
            .prot(prot)
            .fat(fat)
            .sat_fat(sat_fat)
            .trans_fat(trans_fat)
            .kcal(kcal)
            .nat(nat)
            .sugar(sugar)
            .animal_plant(animal_plant)
            .build();
    }


}
