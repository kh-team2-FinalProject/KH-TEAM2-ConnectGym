package com.khteam2.connectgym;

import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.lesson.LessonService;
import com.khteam2.connectgym.member.MemberService;
import com.khteam2.connectgym.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class CommonController {

    private final MemberService memberService;
    private final LessonService lessonService;

    //메인
    @GetMapping("/")
    public String index() {
        return "content/main";
    }

    //레슨 페이지 내 메뉴 이동
    @GetMapping("/lesson")
    public String lesson() {
        return "redirect:/lesson/health";
    }

    /*@GetMapping("/lesson/health")
    public String lessonHealth(Model model) {
        String lessonCategory = "health";
        model.addAttribute("lessonCategory", lessonCategory);
        return "lesson/health";
    }

    @GetMapping("/lesson/yoga")
    public String lessonYoga(Model model) {
        String lessonCategory = "yoga";
        model.addAttribute("lessonCategory", lessonCategory);
        return "lesson/yoga";
    }

    @GetMapping("/lesson/pilates")
    public String lessonPilates(Model model) {
        String lessonCategory = "pilates";
        model.addAttribute("lessonCategory", lessonCategory);
        return "lesson/pilates";
    }*/

    @GetMapping("/enterroom/{lessonNo}")
    public String enterRoom(@PathVariable Long lessonNo, Model model) {
        Lesson lesson = lessonService.getLesson(lessonNo);
        model.addAttribute("lesson", lesson);
        MemberResponse member = memberService.findOneMember(1L);
        model.addAttribute("member", member);
        System.out.println("엔터룸 컨트롤러 호출");
        return "room/enterroom";
    }

    @GetMapping("/mypage/messages")
    public String chattingRoomList(Model model) {
        //배너타이틀
        model.addAttribute("bannerTitle", "MY CHATTIING");
        MemberResponse member = memberService.findOneMember(1L);
        model.addAttribute("member", member);
        System.out.println("member = " + member.getUserName());
        System.out.println("마이레슨리스트 컨트롤러 호출");

        return "mypage/messages";
    }


    @GetMapping("/fooddiary")
    public String fooddiary() {
        return "redirect:/fooddiary/calendar";
    }



}
