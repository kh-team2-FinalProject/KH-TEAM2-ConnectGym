package com.khteam2.connectgym.trainer;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.lesson.dto.LessonResponseDTO;
import com.khteam2.connectgym.trainer.dto.TrainerEnterRoomDto;
import com.khteam2.connectgym.trainer.dto.TrainerEnterRoomRequestDto;
import com.khteam2.connectgym.trainer.dto.TrainerRoomResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class TrainerTestController {

    private final TrainerTestService trainerTestService;

    //트레이너 페이지
    @GetMapping("/trainer/registered")
    public String registered(HttpSession session, Model model) {

        // 등록강좌 불러오기
        LessonResponseDTO registered = trainerTestService.registered(session);


        if (registered.getErrorMsg().equals("NotFound")) {
            model.addAttribute("lesson", null);
        } else {
            model.addAttribute("lesson", registered);
        }

        return "trainerOnlyView1";
    }


    @GetMapping("/trainer/memberList/{lessonNo}")
    public String memberList(@PathVariable Long lessonNo, Model model, HttpSession session) {

        Long trainerNo = (Long) session.getAttribute(SessionConstant.LOGIN_MEMBER_NO);

        // 멤버 정보
        TrainerEnterRoomDto trainerEnterRoomDto = trainerTestService.enrollMemList(lessonNo, trainerNo);
        model.addAttribute("trainerEnterRoom", trainerEnterRoomDto);

        return "trainerOnlyView2";

    }

    // 룸 입장(없을 시 생성, 있을 시 상태 ACTIVE)
    @ResponseBody
    @PostMapping("/trainer/checkRoom")
    public TrainerRoomResponseDto checkRoom(@RequestBody TrainerEnterRoomRequestDto trainerEnterRoomRequestDto) {
        System.out.println("titleCode = " + trainerEnterRoomRequestDto.getTitleCode());
        System.out.println("enrollKey = " + trainerEnterRoomRequestDto.getEnrollKey());
        TrainerRoomResponseDto roomResponseDto
            = trainerTestService.createOrUptedaRoom(trainerEnterRoomRequestDto.getTitleCode(),
            trainerEnterRoomRequestDto.getEnrollKey());
        return roomResponseDto;

    }


}
