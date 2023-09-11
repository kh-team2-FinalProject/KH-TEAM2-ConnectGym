package com.khteam2.connectgym.dietlist.model;

import com.khteam2.connectgym.common.CommonUtil;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Data
/*NoArgsConstructor*/
@ToString
@Builder
public class FoodNutrientFoodDto {
    private Long foodCd; // 식품 번호

    @NotNull
    private String foodNm; // 식품 이름

    @NotNull
    private Double foodSize; // 1회 제공량

    @NotNull
    private Double kcal; // 칼로리

    @NotNull
    private Double choc; // 탄수화물

    @NotNull
    private Double prot; // 단백질

    @NotNull
    private Double fat; // 지방

    private Double sugar; // 설탕

    private Double nat; // 나트륨

    private Double satFat; // 포화지방

    private Double transFat; // 트랜스 지방

    private String animalPlant; // 가공업체

    public static FoodNutrientFoodDto ofOpenDataFoodNutrientItemDto(FoodNutrientItemDto dto) {
        String animalPlant = dto.getAnimalPlant();

        return FoodNutrientFoodDto.builder()
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


    public static FoodNutrientFoodDto foodInfosave(Long foodCd, String foodNm, Double foodSize, Double kcal,
                                                   Double choc, Double prot, Double fat, Double sugar, Double nat, Double satFat,
                                                   Double transFat, String animalPlant) {
        return FoodNutrientFoodDto.builder()
            .foodCd(foodCd)
            .foodNm(foodNm)
            .foodSize(foodSize)
            .kcal(kcal)
            .choc(choc)
            .prot(prot)
            .fat(fat)
            .sugar(sugar)
            .nat(nat)
            .satFat(satFat)
            .transFat(transFat)
            .animalPlant(animalPlant)
            .build();
    }
}
