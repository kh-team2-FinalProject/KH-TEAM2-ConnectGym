package com.khteam2.connectgym;

import com.khteam2.connectgym.follow.FollowService;
import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.lesson.LessonService;
import com.khteam2.connectgym.member.MemberService;
import com.khteam2.connectgym.member.dto.MemberResponseDTO;
import com.khteam2.connectgym.trainer.dto.TrainerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommonController {

    private final MemberService memberService;
    private final FollowService followService;
    //메인
    @GetMapping("/")
    public String index() {
        return "content/main";
    }
    @GetMapping("/mypage/myFollowingTest")
    public String myFolloing(Model model, HttpSession session) {
        //배너타이틀
        model.addAttribute("bannerTitle", "following");

        MemberResponseDTO member = memberService.sessionMem(session);

        List<TrainerResponseDTO> following = followService.followingList(member.getNo());

        model.addAttribute("following", following);

        return "mypage/following_test";
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


    @GetMapping("/mypage/messages")
    public String chattingRoomList(Model model) {
        //배너타이틀
        model.addAttribute("bannerTitle", "MY CHATTIING");
        MemberResponseDTO member = memberService.findOneMember(1L);
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
