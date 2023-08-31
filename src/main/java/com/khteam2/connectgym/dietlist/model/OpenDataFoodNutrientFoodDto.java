package com.khteam2.connectgym.dietlist.model;

import com.khteam2.connectgym.common.CommonUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenDataFoodNutrientFoodDto {
    /**
     * 식품 이름
     */
    private String foodName;
    /**
     * 1회 제공량 (g)
     */
    private Double oneTimeOffer;
    /**
     * 열량
     */
    private Double calorie;
    /**
     * 탄수화물
     */
    private Double carbohydrate;
    /**
     * 단백질
     */
    private Double protein;
    /**
     * 지방
     */
    private Double fat;
    /**
     * 당류 (g)
     */
    private Double sugars;
    /**
     * 나트륨 (mg)
     */
    private Double natrium;
    /**
     * 포화지방산 (g)
     */
    private Double saturatedFattyAcids;
    /**
     * 트랜스지방산 (g)
     */
    private Double transFat;
    /**
     * 가공업체
     */
    private String company;

    public static OpenDataFoodNutrientFoodDto ofOpenDataFoodNutrientItemDto(OpenDataFoodNutrientItemDto dto) {
        String animalPlant = dto.getAnimalPlant();

        return OpenDataFoodNutrientFoodDto.builder()
                .foodName(dto.getDescKor())
                .oneTimeOffer(CommonUtil.opendataParseDouble(dto.getServingWt()))
                .calorie(CommonUtil.opendataParseDouble(dto.getNutrCont1()))
                .carbohydrate(CommonUtil.opendataParseDouble(dto.getNutrCont2()))
                .protein(CommonUtil.opendataParseDouble(dto.getNutrCont3()))
                .fat(CommonUtil.opendataParseDouble(dto.getNutrCont4()))
                .sugars(CommonUtil.opendataParseDouble(dto.getNutrCont5()))
                .natrium(CommonUtil.opendataParseDouble(dto.getNutrCont6()))
                .saturatedFattyAcids(CommonUtil.opendataParseDouble(dto.getNutrCont8()))
                .transFat(CommonUtil.opendataParseDouble(dto.getNutrCont9()))
                .company((animalPlant != null && !animalPlant.isEmpty()) ? animalPlant : null)
                .build();
    }
}
