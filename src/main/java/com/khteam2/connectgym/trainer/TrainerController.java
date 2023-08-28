package com.khteam2.connectgym.trainer;

import com.khteam2.connectgym.trainer.dto.TrainerRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TrainerController {

    @GetMapping("/convertTrainer")
    public String convertTrainer(Model model){
        model.addAttribute("bannerTitle", "convert");
        return "mypage/convertToTrainerAccount";
    }

    @GetMapping(value = "/trainerDetail")
    public String lessonDetail(Model model) {
        //배너타이틀
        model.addAttribute("bannerTitle", "trainer");
        return "detailOrCrud/trainerdetail";
    }


    @PostMapping(value = "/trainerDetail")
    public String lessonDetail(Model model, TrainerRequest trainerRequest) {
        //배너타이틀
        model.addAttribute("bannerTitle", "trainer");

        //service save() 호출
        return "detailOrCrud/createComplete";
    }

}
