package com.khteam2.connectgym.dietlist.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DietListFoodDto {
    private Long memberFoodNo;
    private FoodDto food;
}
