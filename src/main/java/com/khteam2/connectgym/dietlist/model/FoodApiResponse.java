package com.khteam2.connectgym.dietlist.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// 필드명 달라서 매핑
@Data
public class FoodApiResponse {

    @JsonProperty("DESC_KOR")
    private String foodNm;
    @JsonProperty("SERVING_WT")
    private double foodSize;
    @JsonProperty("NUTR_CONT2")
    private double choc;
    @JsonProperty("NUTR_CONT3")
    private double prot;
    @JsonProperty("NUTR_CONT4")
    private double fat;
    @JsonProperty("NUTR_CONT8")
    private double satFat;
    @JsonProperty("NUTR_CONT9")
    private double transFat;
    @JsonProperty("NUTR_CONT1")
    private double kcal;
    @JsonProperty("NUTR_CONT6")
    private double nat;
    @JsonProperty("NUTR_CONT5")
    private double sugar;
    @JsonProperty("ANIMAL_PLANT")
    private String animalPlant;
}
