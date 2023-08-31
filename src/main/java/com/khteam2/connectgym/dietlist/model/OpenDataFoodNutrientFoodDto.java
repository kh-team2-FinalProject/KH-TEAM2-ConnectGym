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
     * 식품 번호
     */
    private Long foodCd;
    /**
     * 식품 이름
     */
    private String foodNm;
    /**
     * 1회 제공량 (g)
     */
    private Double foodSize;
    /**
     * 열량
     */
    private Double kcal;
    /**
     * 탄수화물
     */
    private Double choc;
    /**
     * 단백질
     */
    private Double prot;
    /**
     * 지방
     */
    private Double fat;
    /**
     * 당류 (g)
     */
    private Double sugar;
    /**
     * 나트륨 (mg)
     */
    private Double nat;
    /**
     * 포화지방산 (g)
     */
    private Double sat_fat;
    /**
     * 트랜스지방산 (g)
     */
    private Double trans_fat;
    /**
     * 가공업체
     */
    private String animal_plant;

    public static OpenDataFoodNutrientFoodDto ofOpenDataFoodNutrientItemDto(OpenDataFoodNutrientItemDto dto) {
        String animalPlant = dto.getAnimalPlant();

        return OpenDataFoodNutrientFoodDto.builder()
            .foodNm(dto.getDescKor())
            .foodSize(CommonUtil.opendataParseDouble(dto.getServingWt()))
            .kcal(CommonUtil.opendataParseDouble(dto.getNutrCont1()))
            .choc(CommonUtil.opendataParseDouble(dto.getNutrCont2()))
            .prot(CommonUtil.opendataParseDouble(dto.getNutrCont3()))
            .fat(CommonUtil.opendataParseDouble(dto.getNutrCont4()))
            .sugar(CommonUtil.opendataParseDouble(dto.getNutrCont5()))
            .nat(CommonUtil.opendataParseDouble(dto.getNutrCont6()))
            .sat_fat(CommonUtil.opendataParseDouble(dto.getNutrCont8()))
            .trans_fat(CommonUtil.opendataParseDouble(dto.getNutrCont9()))
            .animal_plant((animalPlant != null && !animalPlant.isEmpty()) ? animalPlant : null)
            .build();
    }

    public Food toEntity() {
        return Food.builder()
            .foodCd(this.foodCd)
            .foodNm(this.foodNm)
            .foodSize(this.foodSize)
            .kcal(this.kcal)
            .choc(this.choc)
            .prot(this.prot)
            .fat(this.fat)
            .sugar(this.sugar)
            .nat(this.nat)
            .sat_fat(this.sat_fat)
            .trans_fat(this.trans_fat)
            .animal_plant(this.animal_plant)
            .build();
    }
}
