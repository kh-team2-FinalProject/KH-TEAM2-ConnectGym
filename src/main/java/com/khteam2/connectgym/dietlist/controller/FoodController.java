package com.khteam2.connectgym.dietlist.controller;

import com.khteam2.connectgym.dietlist.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
