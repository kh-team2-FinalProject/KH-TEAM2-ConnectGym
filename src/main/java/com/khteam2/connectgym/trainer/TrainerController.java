package com.khteam2.connectgym.trainer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TrainerController {

    @GetMapping(value = "/trainerDetail")
    public String lessonDetail(Model model) {
        //배너타이틀
        model.addAttribute("bannerTitle", "trainer detail");
        return "detailOrCrud/trainerdetail";
    }

}
