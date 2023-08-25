package com.khteam2.connectgym.fooddiary;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DietListController {


    @GetMapping("/fooddiary/dietlist")
    public String diet_WriteForm() {

        return "/fooddiary/dietlist";
    }



}
