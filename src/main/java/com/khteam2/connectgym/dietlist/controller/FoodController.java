package com.khteam2.connectgym.dietlist.controller;

import com.khteam2.connectgym.dietlist.model.Food;
import com.khteam2.connectgym.dietlist.model.FoodNutrientDto;
import com.khteam2.connectgym.dietlist.repository.FoodRepository;
import com.khteam2.connectgym.dietlist.service.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }


    @GetMapping(value = "/api/food_test_2/{pageNo}")
    public Object foodTest2(
        @PathVariable Integer pageNo) {
        FoodNutrientDto dto = this.foodService.getFoods(pageNo, 100);

        return dto;
    }

    @GetMapping(value = "/api/renewFoodData")
    public Object renewFoodData() {
        return this.foodService.moveDataToDatabase();
    }

}
