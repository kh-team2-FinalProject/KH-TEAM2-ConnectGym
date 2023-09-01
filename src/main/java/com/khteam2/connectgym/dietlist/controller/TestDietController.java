
package com.khteam2.connectgym.dietlist.controller;

import com.khteam2.connectgym.dietlist.model.Food;
import com.khteam2.connectgym.dietlist.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TestDietController {
    private final FoodService foodService;

    @Autowired
    public TestDietController(FoodService foodService) {
        this.foodService = foodService;
    }

/*    @GetMapping("/fooddiary/dietlisttest")
    public String showDietList(Model model) {
        List<Food> foods = foodService.saveFoodsFromOpenAPI();
        model.addAttribute("foods", foods);
        return "fooddiary/dietlisttest";
    }*/
}

