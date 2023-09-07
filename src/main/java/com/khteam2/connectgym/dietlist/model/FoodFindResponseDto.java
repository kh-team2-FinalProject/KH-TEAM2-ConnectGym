package com.khteam2.connectgym.dietlist.model;

import com.khteam2.connectgym.common.Pagination;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FoodFindResponseDto {
    private boolean success;
    private String message;
    private Pagination pagination;
    private List<FoodDto> foods;
}
