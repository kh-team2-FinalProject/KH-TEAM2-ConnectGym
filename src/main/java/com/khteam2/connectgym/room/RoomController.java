package com.khteam2.connectgym.room;

import com.khteam2.connectgym.member.MemberClass;
import com.khteam2.connectgym.member.MemberService;
import com.khteam2.connectgym.member.dto.MemberResponseDto;
import com.khteam2.connectgym.room.dto.RoomResponseDto;
import com.khteam2.connectgym.trainer.TrainerService;
import com.khteam2.connectgym.trainer.dto.TrainerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class RoomController {
    private final MemberService memberService;
    private final TrainerService trainerService;
    private final RoomService roomService;

    @GetMapping("/enterRoom/{roomNo}")
    public String enterRoom(@PathVariable Long roomNo, Model model, HttpSession session) {
        RoomResponseDto roomResponseDto = roomService.enterRoomInfo(roomNo);

        model.addAttribute("roomInfo", roomResponseDto);

        Long no = (Long) session.getAttribute("session_login_member_no");
        MemberClass value = (MemberClass) session.getAttribute("session_login_member_class");

        if (value == MemberClass.TRAINER) {
            TrainerResponseDto trainer = trainerService.findOneTrainer(no);
            model.addAttribute("trainer", trainer);
            model.addAttribute("type", "trainer");
        } else {
            MemberResponseDto member = memberService.findOneMember(no);
            model.addAttribute("member", member);
            model.addAttribute("type", "member");
        }

        System.out.println("엔터룸 컨트롤러 호출");

        return "room/enterroom";
    }
}
