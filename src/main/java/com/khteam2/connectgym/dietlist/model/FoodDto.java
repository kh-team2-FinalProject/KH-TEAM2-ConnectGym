package com.khteam2.connectgym.dietlist.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FoodDto {
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

    public Food toEntity() {
        return Food.builder()
            .foodCd(foodCd)
            .foodNm(foodNm)
            .foodSize(foodSize)
            .choc(choc)
            .prot(prot)
            .fat(fat)
            .satFat(satFat)
            .transFat(transFat)
            .kcal(kcal)
            .nat(nat)
            .sugar(sugar)
            .animalPlant(animalPlant)
            .build();
    }

    public static FoodDto of(Food food) {
        return FoodDto.builder()
            .foodCd(food.getFoodCd())
            .foodNm(food.getFoodNm())
            .foodSize(food.getFoodSize())
            .choc(food.getChoc())
            .prot(food.getProt())
            .fat(food.getFat())
            .satFat(food.getSatFat())
            .transFat(food.getTransFat())
            .kcal(food.getKcal())
            .nat(food.getNat())
            .sugar(food.getSugar())
            .animalPlant(food.getAnimalPlant())
            .build();
    }
}
