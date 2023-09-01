package com.khteam2.connectgym.dietlist.model;

import com.khteam2.connectgym.common.CommonUtil;
import lombok.*;

@Data
@NoArgsConstructor
/*AllArgsConstructor*/
@ToString
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
    private Double satFat;
    /**
     * 트랜스지방산 (g)
     */
    private Double transFat;
    /**
     * 가공업체
     */
    private String animalPlant;

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
            .satFat(CommonUtil.opendataParseDouble(dto.getNutrCont8()))
            .transFat(CommonUtil.opendataParseDouble(dto.getNutrCont9()))
            .animalPlant((animalPlant != null && !animalPlant.isEmpty()) ? animalPlant : null)
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
            .satFat(this.satFat)
            .transFat(this.transFat)
            .animalPlant(this.animalPlant)
            .build();
    }

    @Builder
    public OpenDataFoodNutrientFoodDto(Long foodCd, String foodNm, Double foodSize, Double kcal,
                   Double choc, Double prot, Double fat, Double sugar, Double nat, Double satFat,
                   Double transFat, String animalPlant){
        this.foodCd = foodCd;
        this.foodNm = foodNm;
        this.foodSize = foodSize;
        this.kcal = kcal;
        this.choc = choc;
        this.prot = prot;
        this.fat = fat;
        this.sugar = sugar;
        this.nat = nat;
        this.satFat = satFat;
        this.transFat = transFat;
        this.animalPlant = animalPlant;
    }

}
