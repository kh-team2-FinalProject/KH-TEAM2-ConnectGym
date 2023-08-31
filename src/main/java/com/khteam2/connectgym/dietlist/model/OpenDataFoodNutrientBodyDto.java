package com.khteam2.connectgym.dietlist.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpenDataFoodNutrientBodyDto {
    private Integer pageNo;
    private Integer totalCount;
    private Integer numOfRows;
    @JsonProperty(value = "items")
    @JsonDeserialize(as = ArrayList.class, contentAs = OpenDataFoodNutrientItemDto.class)
    private List<OpenDataFoodNutrientItemDto> items;
}
