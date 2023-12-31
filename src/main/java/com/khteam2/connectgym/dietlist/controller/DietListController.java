package com.khteam2.connectgym.dietlist.controller;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.dietlist.model.DietListResponseDto;
import com.khteam2.connectgym.dietlist.model.Food;
import com.khteam2.connectgym.dietlist.model.FoodFindRequestDto;
import com.khteam2.connectgym.dietlist.model.FoodFindResponseDto;
import com.khteam2.connectgym.dietlist.repository.FoodRepository;
import com.khteam2.connectgym.dietlist.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class DietListController {
    private final FoodService foodService;
    private final FoodRepository foodRepository;

    @GetMapping("fooddiary/foodInfo")
    public String diet_SearchForm(Model model, FoodFindRequestDto requestDto, Food foodForm) {

        String foodDCategory = "FoodInfo";
        model.addAttribute("lessonCategory", foodDCategory);

        FoodFindResponseDto responseDto = this.foodService.findFood(requestDto);

        model.addAttribute("responseDto", responseDto);


        model.addAttribute("food", new Food());

        return "fooddiary/foodInfo";
    }

    @PostMapping("fooddiary/foodInfo")
    public String addFood(
        @ModelAttribute("food")
        @Valid Food food,
        BindingResult result,
        Model model,
        FoodFindRequestDto requestDto) throws Exception {
        FoodFindResponseDto responseDto = this.foodService.findFood(requestDto);

        model.addAttribute("responseDto", responseDto);

        /* 에러 메세지 */
        if (result.hasErrors()) {
           model.addAttribute("food", food);

            return "fooddiary/foodInfo";
        }
        foodService.saveFood(food);
        return "fooddiary/foodInfo";
    }

    // dietlist
    @GetMapping("fooddiary/dietlist")
    public String searchDiet(
        Model model,
        @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long loginMemberNo,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        String foodDCategory = "DietList";
        model.addAttribute("lessonCategory", foodDCategory);


        if (date == null) {
            // 파라미터가 없으면 현재 날짜를 기준으로 보여준다.
            date = LocalDate.now();
        }

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
