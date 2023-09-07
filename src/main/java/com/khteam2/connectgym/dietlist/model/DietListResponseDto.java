package com.khteam2.connectgym.dietlist.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DietListResponseDto {
    private boolean success;
    private String message;
    private List<FoodDto> breakfastList;
    private List<FoodDto> lunchList;
    private List<FoodDto> dinnerList;
    private List<FoodDto> snackList;
}
