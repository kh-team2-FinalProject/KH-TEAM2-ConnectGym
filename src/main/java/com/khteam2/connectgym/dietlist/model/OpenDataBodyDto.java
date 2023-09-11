package com.khteam2.connectgym.dietlist.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class OpenDataBodyDto {
    private Integer pageNo;
    private Integer totalCount;
    private Integer numOfRows;
    @JsonProperty(value = "items")
    @JsonDeserialize(as = ArrayList.class, contentAs = FoodNutrientItemDto.class)
    private List<FoodNutrientItemDto> items;
}
