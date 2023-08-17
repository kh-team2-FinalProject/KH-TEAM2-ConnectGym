package com.khteam2.connectgym;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {


    @GetMapping("/")
    public String index(){
        return "content/main";
    }

    @GetMapping("/lesson")
    public String lesson(){
        return "content/lessonlist";
    }

    @GetMapping("/lesson/health")
    public String lessonHealth(){
        return "lesson/health";
    }

    @GetMapping("/lesson/pilates")
    public String lessonPilates(){
        return "lesson/pilates";
    }

    @GetMapping("/lesson/yoga")
    public String lessonYoga(){
        return "lesson/yoga";
    }
}
