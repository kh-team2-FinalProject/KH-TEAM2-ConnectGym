//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
    @Autowired
    private FoodService foodService;

    public TestDietController() {
    }

    @GetMapping({"/fooddiary/dietlisttest"})
    public String showDietList(Model model) {
        List<Food> foods = this.foodService.getAllFoods();
        model.addAttribute("foods", foods);
        return "fooddiary/dietlisttest";
    }
}
