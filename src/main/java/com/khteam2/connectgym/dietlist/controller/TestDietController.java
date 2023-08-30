
package com.khteam2.connectgym.dietlist.controller;

import com.khteam2.connectgym.dietlist.model.Food;
import com.khteam2.connectgym.dietlist.model.FoodEntity;
import com.khteam2.connectgym.dietlist.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

@Controller
public class TestDietController {
    private final FoodService foodService;

    @Autowired
    public TestDietController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/fooddiary/dietlisttest")
    public String showDietList(Model model) {
        List<FoodEntity> foods = foodService.getAllFoods();
        model.addAttribute("foods", foods);
        return "fooddiary/dietlisttest"; // Assuming dietlisttest is the view name
    }

/*    @PostMapping("/fooddiary/dietlisttest")
    public String load(@RequestParam("data") String data, Model model){
        String result ="";

        try {
            String requestData = data;
            URL url = new URL("http://apis.data.go.kr/1470000/FoodNtrIrdntInfoService/"
                + "zYPK1Bj9cpL%2FZ1nD8%2Fr56or2XJaCFvizZqM9ZQ4oxjDhjtfMHceoEtq4%2BrwcNJoynkMj5DWZk9EIxH8bwPzs8Q%3D%3D/xml");
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
            result = bf.readLine();
        }
    }
*/
}
