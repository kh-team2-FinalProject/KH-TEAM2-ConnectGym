package com.khteam2.connectgym.dietlist.controller;
import com.khteam2.connectgym.dietlist.model.Food;
import com.khteam2.connectgym.dietlist.repository.FoodRepository;
import com.khteam2.connectgym.dietlist.service.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class DietListController {

    private final FoodService foodService;
    @Autowired
    private FoodRepository foodRepository;



    @GetMapping("fooddiary/foodInfo")
    public String diet_SearchForm(Model model){
        model.addAttribute("food", new Food());
        return "fooddiary/foodInfo";
    }

    @PostMapping("fooddiary/foodInfo")
    public String addFood(@ModelAttribute @Valid Food food, Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("food", food); // 폼 데이터를 모델에 추가
            // 유효성 통과 못한 필드와 메세지 핸들링
            Map<String, String> validatorResult = foodService.validateHandling(errors);
            for(String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "fooddiary/foodInfo"; // 다시 입력 페이지로 이동
        }

        foodService.saveFood(food);

        return "redirect:/fooddiary/foodInfo";
    }


    @GetMapping("fooddiary/dietlist")
    public String diet_WriteForm(Model model) {
        Food food = foodRepository.findById(29057L).orElse(null);
        List<Food> foods = new ArrayList<>();
        foods.add(food);
        model.addAttribute("foods", foods);

        return "fooddiary/dietlist";
    }

    @GetMapping("fooddiary/foodinfo")
    public String searchFood(@RequestParam String key, Model model){
        if (key != null && !key.isEmpty()) {
            List<Food> foodinfo = foodService.searchFood(key);
            model.addAttribute("foodinfo", foodinfo);
        }
        model.addAttribute("food", new Food());
        return "fooddiary/foodinfo";
    }



}
