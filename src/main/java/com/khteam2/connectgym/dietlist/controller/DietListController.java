package com.khteam2.connectgym.dietlist.controller;
import com.khteam2.connectgym.dietlist.model.Food;
import com.khteam2.connectgym.dietlist.repository.FoodRepository;
import com.khteam2.connectgym.dietlist.service.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class DietListController {

    private final FoodService foodService;
    @Autowired
    private FoodRepository foodRepository;

    @GetMapping("/fooddiary/dietlist")
    public String diet_WriteForm(Model model) {
        Food food = foodRepository.findById(29057L).orElse(null);
        List<Food> foods = new ArrayList<>();
        foods.add(food);
        model.addAttribute("foods", foods);

        return "/fooddiary/dietlist";
    }

    @GetMapping("/fooddiary/foodInfo")
    public String diet_SearchForm(Model model){
        model.addAttribute("food", new Food());
        return "fooddiary/foodInfo";
    }

    @PostMapping("/addFoodinfo")
    public String addFood(@ModelAttribute Food food) {
        foodService.saveFood(food);
        return "redirect:/fooddiary/foodInfo";
    }


}
