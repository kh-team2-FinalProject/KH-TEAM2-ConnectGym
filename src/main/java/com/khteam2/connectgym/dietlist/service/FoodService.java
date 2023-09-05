package com.khteam2.connectgym.dietlist.service;

import com.khteam2.connectgym.dietlist.model.Food;
import com.khteam2.connectgym.dietlist.model.FoodNutrientDto;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import java.awt.print.Pageable;
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

}
