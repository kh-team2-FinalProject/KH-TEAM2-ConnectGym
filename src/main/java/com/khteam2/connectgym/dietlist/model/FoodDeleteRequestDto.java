package com.khteam2.connectgym.dietlist.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FoodDeleteRequestDto {
    private Long memberFoodNo;
}
