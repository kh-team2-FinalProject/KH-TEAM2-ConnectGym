package com.khteam2.connectgym.dietlist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenDataFoodNutrientItemDto {
    @JsonProperty(value = "DESC_KOR")
    private String descKor;
    @JsonProperty(value = "SERVING_WT")
    private String servingWt;
    @JsonProperty(value = "NUTR_CONT1")
    private String nutrCont1;
    @JsonProperty(value = "NUTR_CONT2")
    private String nutrCont2;
    @JsonProperty(value = "NUTR_CONT3")
    private String nutrCont3;
    @JsonProperty(value = "NUTR_CONT4")
    private String nutrCont4;
    @JsonProperty(value = "NUTR_CONT5")
    private String nutrCont5;
    @JsonProperty(value = "NUTR_CONT6")
    private String nutrCont6;
    @JsonProperty(value = "NUTR_CONT7")
    private String nutrCont7;
    @JsonProperty(value = "NUTR_CONT8")
    private String nutrCont8;
    @JsonProperty(value = "NUTR_CONT9")
    private String nutrCont9;
    @JsonProperty(value = "BGN_YEAR")
    private String bgnYear;
    @JsonProperty(value = "ANIMAL_PLANT")
    private String animalPlant;
}
