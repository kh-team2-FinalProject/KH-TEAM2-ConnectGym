package com.khteam2.connectgym.dietlist.controller;

import com.khteam2.connectgym.dietlist.model.OpenDataFoodNutrientDto;
import com.khteam2.connectgym.dietlist.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FoodController {
    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/fetch-and-save-foods")
    public String fetchAndSaveFoods() {
        foodService.saveFoodsFromOpenAPI();  // OpenAPI에서 음식 데이터를 가져와 데이터베이스에 저장
        return "Foods fetched and saved successfully!";
    }

    @GetMapping(value = "/api/food_test_2/{pageNo}")
    public Object foodTest2(
        @PathVariable Integer pageNo) {
        OpenDataFoodNutrientDto dto = this.foodService.getFoods(pageNo, 100);

        return dto;
    }

    @GetMapping(value = "/api/renewFoodData")
    public Object renewFoodData() {
        return this.foodService.moveDataToDatabase();
    }
}
