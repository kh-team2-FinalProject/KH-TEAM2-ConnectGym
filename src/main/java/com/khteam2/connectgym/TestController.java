package com.khteam2.connectgym;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

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

    @GetMapping("/mypage/mylessons")
    public String myLesson() {
        return "mypage/mylessons";
    }

    @GetMapping("/enterRoom")
    public String enterRoom(Model model) {
        // 현재 접속중인 회원(=>세션에서 가져오기)의 수강중인 레슨의 방이름 / 회원이름
        String roomName = "fixedname";
        String userName = "이서희";

        model.addAttribute("roomName", roomName);
        model.addAttribute("userRole", "member");
        model.addAttribute("userName", userName);
        return "room/enterroom";
    }

}
