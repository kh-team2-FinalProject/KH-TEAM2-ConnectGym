package com.khteam2.connectgym;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    //메인
    @GetMapping("/")
    public String index() {
        return "testview";
    }

/*
    //메인
    @GetMapping("/")
    public String index() {
        return "content/main";
    }
    //레슨 페이지 내 메뉴 이동
    @GetMapping("/lesson")
    public String lesson() {
        return "content/lessonlist";
    }

    @GetMapping("/lesson/health")
    public String lessonHealth() {
        return "lesson/health";
    }

    @GetMapping("/lesson/pilates")
    public String lessonPilates() {
        return "lesson/pilates";
    }

    @GetMapping("/lesson/yoga")
    public String lessonYoga() {
        return "lesson/yoga";
    }

    //마이페이지 내 메뉴 이동
    @GetMapping("/mypage")
    public String myPage() {
        return "content/mypages";
    }

    @GetMapping("/mypage/mylesson")
    public String myLesson() {
        return "mypage/mylesson";
    }
    */
}
