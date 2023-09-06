package com.khteam2.connectgym.dietlist.service;

import com.khteam2.connectgym.dietlist.model.Food;
import com.khteam2.connectgym.dietlist.model.FoodInsertRequestDto;
import com.khteam2.connectgym.dietlist.model.FoodInsertResponseDto;
import com.khteam2.connectgym.dietlist.model.FoodNutrientDto;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;

// OpenAPI에서 음식 데이터를 가져와 저장하는 메소드


public interface FoodService {

    FoodNutrientDto getFoods(int pageNo, int limit);

    int moveDataToDatabase();


    /* db 저장 */
    Food saveFood(Food food);

    Map<String, String> validateHandling(Errors errors);


    List<Food> searchFood(String key);

    List<Food> searchDiet(String key);

    Food selectFood(Long selectedKey);

    default FoodInsertResponseDto insertFood(FoodInsertRequestDto requestDto, Long loginMemberNo) {
        return null;
    }
}
