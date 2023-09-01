package com.khteam2.connectgym.room;

import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.lesson.LessonService;
import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.member.MemberClass;
import com.khteam2.connectgym.member.MemberService;
import com.khteam2.connectgym.member.dto.MemberResponseDTO;
import com.khteam2.connectgym.trainer.Trainer;
import com.khteam2.connectgym.trainer.TrainerService;
import com.khteam2.connectgym.trainer.dto.TrainerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class RoomController {

    private final LessonService lessonService;
    private final MemberService memberService;
    private final TrainerService trainerService;

    @GetMapping("/enterroom/{lessonNo}")
    public String enterRoom(@PathVariable Long lessonNo, Model model, HttpSession session) {
        Lesson lesson = lessonService.getLesson(lessonNo);
        model.addAttribute("lesson", lesson);

        Long no  = (Long) session.getAttribute("session_login_member_no");
        MemberClass value = (MemberClass) session.getAttribute("session_login_member_class");

        if(value == MemberClass.TRAINER){
            TrainerResponseDTO trainer = trainerService.findOneTrainer(no);
            model.addAttribute("trainer", trainer);
            model.addAttribute("type", "trainer");
        } else {
            MemberResponseDTO member = memberService.findOneMember(no);
            model.addAttribute("member", member);
            model.addAttribute("type", "member");
        }

        System.out.println("엔터룸 컨트롤러 호출");
        return "room/enterroom";
    }
}
