package com.khteam2.connectgym.dietlist.controller;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.dietlist.model.DietListResponseDto;
import com.khteam2.connectgym.dietlist.model.Food;
import com.khteam2.connectgym.dietlist.repository.FoodRepository;
import com.khteam2.connectgym.dietlist.service.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class DietListController {

    private final FoodService foodService;
    @Autowired
    private FoodRepository foodRepository;


    @GetMapping("fooddiary/foodInfo")
    public String diet_SearchForm(@RequestParam(required = false) String key, Model model) {
        if (key != null && !key.isEmpty()) {
            List<Food> foodinfo = foodService.searchFood(key);
            model.addAttribute("foodinfo", foodinfo);
        }

        model.addAttribute("food", new Food());
        return "fooddiary/foodInfo";
    }
/*
    @GetMapping("fooddiary/dietlist")
    public String my_DietList(Model model){
        model.addAttribute("food", new Food());
        return "fooddiary/dietlist";
    }
*/


    @PostMapping("fooddiary/foodInfo")
    public String addFood(@ModelAttribute @Valid Food food, Errors errors, Model model) {
        /* 에러 메세지 */
        if (errors.hasErrors()) {
            model.addAttribute("food", food);
            Map<String, String> validatorResult = foodService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "fooddiary/foodInfo";
        }

        foodService.saveFood(food);

        return "redirect:/fooddiary/foodInfo";
    }


/*    @GetMapping("fooddiary/dietwrite")
    public String diet_WriteForm(Model model) {
        Food food = foodRepository.findById(29057L).orElse(null);
        List<Food> foods = new ArrayList<>();
        foods.add(food);
        model.addAttribute("foods", foods);

        return "fooddiary/dietlist";
    }*/

//    @GetMapping("fooddiary/foodinfo")
//    public String searchFood(@RequestParam String key, Model model) {
//        if (key != null && !key.isEmpty()) {
//            List<Food> foodinfo = foodService.searchFood(key);
//            model.addAttribute("foodinfo", foodinfo);
//        }
//        model.addAttribute("food", new Food());
//        return "fooddiary/foodinfo";
//    }


    // dietlist
    @GetMapping("fooddiary/dietlist")
    public String searchDiet(
        Model model,
        @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long loginMemberNo,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//        if (key != null && !key.isEmpty()) {
//            List<Food> dietList = foodService.searchDiet(key);
//            model.addAttribute("dietList", dietList);
//        }
        DietListResponseDto responseDto = this.foodService.dietList(loginMemberNo, date);

        model.addAttribute("responseDto", responseDto);
        model.addAttribute("date", date);

        model.addAttribute("selectedFood", null);
        model.addAttribute("food", new Food());

        return "fooddiary/dietlist";
    }


    @PostMapping("/selectfood")
    public String selectFood(
        @RequestParam(name = "selectedKey", required = false) Long selectedKey,
        Model model) {
        Food selectedFood = foodRepository.findByFoodCd(selectedKey);
        model.addAttribute("selectedFood", selectedFood);
        return "redirect:/fooddiary/dietlist";
    }


}
