package com.khteam2.connectgym.dietlist.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpenDataFoodNutrientDto {
    private OpenDataHeaderDto header;
    private OpenDataFoodNutrientBodyDto body;
}
