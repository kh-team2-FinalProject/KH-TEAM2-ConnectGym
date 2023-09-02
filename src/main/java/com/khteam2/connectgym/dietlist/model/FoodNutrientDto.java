package com.khteam2.connectgym.dietlist.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FoodNutrientDto {
    private OpenDataHeaderDto header;
    private OpenDataBodyDto body;
}
