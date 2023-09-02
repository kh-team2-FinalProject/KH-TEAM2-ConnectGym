package com.khteam2.connectgym.dietlist.controller;

import com.khteam2.connectgym.dietlist.model.Food;
import com.khteam2.connectgym.dietlist.model.FoodNutrientDto;
import com.khteam2.connectgym.dietlist.repository.FoodRepository;
import com.khteam2.connectgym.dietlist.service.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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



/*
    @PostMapping("/addFoodinfo")
    public String addFood(
        @RequestParam String foodNm,
        @RequestParam Double foodSize,
        @RequestParam Double choc,
        @RequestParam Double prot,
        @RequestParam Double fat,
        @RequestParam Double satFat,
        @RequestParam Double transFat,
        @RequestParam Double kcal,
        @RequestParam Double nat,
        @RequestParam Double sugar,
        @RequestParam String animalPlant
    ) {
        // 파라미터를 이용하여 Food 객체 생성
        Food food = new Food();
        food.setFoodNm(foodNm);
        food.setFoodSize(foodSize);
        food.setChoc(choc);
        food.setProt(prot);
        food.setFat(fat);
        food.setSatFat(satFat);
        food.setTransFat(transFat);
        food.setKcal(kcal);
        food.setNat(nat);
        food.setSugar(sugar);
        food.setAnimalPlant(animalPlant);

        // FoodService를 사용하여 음식 정보 저장
        foodService.saveFood(food);

        // 음식 정보가 저장된 후의 처리 로직 추가 (예: 다른 페이지로 리다이렉트)

        return "redirect:/fooddiary/foodInfo"; // 적절한 리다이렉트 URL로 변경
    }
*/



}
