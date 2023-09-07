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
    private List<DietListFoodDto> breakfastList;
    private List<DietListFoodDto> lunchList;
    private List<DietListFoodDto> dinnerList;
    private List<DietListFoodDto> snackList;
}
