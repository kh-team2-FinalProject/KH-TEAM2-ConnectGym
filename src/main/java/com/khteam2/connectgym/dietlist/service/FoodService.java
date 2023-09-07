package com.khteam2.connectgym.dietlist.service;

import com.khteam2.connectgym.dietlist.model.*;
import org.springframework.validation.Errors;

import java.time.LocalDate;
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

    default DietListResponseDto dietList(Long loginMemberNo, LocalDate date) {
        return null;
    }

    default FoodFindResponseDto findFood(FoodFindRequestDto requestDto) {
        return null;
    }

    default FoodInsertResponseDto insertFood(FoodInsertRequestDto requestDto, Long loginMemberNo) {
        return null;
    }
}
