package com.khteam2.connectgym.dietlist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodInsertResponseDto {
    private boolean success;
    private String message;
}
