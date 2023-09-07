package com.khteam2.connectgym.trainer;

import com.khteam2.connectgym.chat_test.ChatroomService;
import com.khteam2.connectgym.chat_test.dto.ChatroomDTO;
import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.follow.FollowService;
import com.khteam2.connectgym.lesson.dto.LessonResponseDTO;
import com.khteam2.connectgym.like.LikeService;
import com.khteam2.connectgym.member.MemberClass;
import com.khteam2.connectgym.member.dto.MemberResponseDTO;
import com.khteam2.connectgym.trainer.dto.TrainerEnterRoomRequestDto;
import com.khteam2.connectgym.trainer.dto.TrainerEnterRoomResponseDto;
import com.khteam2.connectgym.trainer.dto.TrainerResponseDTO;
import com.khteam2.connectgym.trainer.dto.TrainerRoomResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/trainerOnly")
public class TrainerOnlyController {

    private static final Logger logger = LoggerFactory.getLogger(TrainerOnlyController.class);


    private final TrainerService trainerService;
    private final TrainerOnlyService trainerOnlyService;
    private final ChatroomService chatroomService;
    private final FollowService followService;
    private final LikeService likeService;


    @GetMapping("/mypage")
    public String myPageT(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER_CLASS, required = false) MemberClass loginMemberClass,
                          @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long trainerNo,
                          Model model, RedirectAttributes redirectAttributes) {


        if (loginMemberClass == null) {

            // 로그인되어 있지 않은 경우
            redirectAttributes.addFlashAttribute("message", "로그인 해주세요.");
            return "redirect:/";

        } else if (loginMemberClass == MemberClass.TRAINER) {
            // 트레이너 회원 로그인 된 경우
            TrainerResponseDTO trainerResponseDTO = trainerService.findOneTrainer(trainerNo);

            List<ChatroomDTO> chatroomList = chatroomService.searchMyMemberChatroomList(trainerNo);
            model.addAttribute("trainer", trainerResponseDTO);
            model.addAttribute("chatroomList", chatroomList);
            return "trainerOnly/myDashboard"; // 트레이너 마이페이지로 이동
        } else {
            return "redirect:/mypage";
        }


    }

    //내가 등록한 강좌 정보
    @GetMapping("/mypage/myLesson")
    public String myLessonT(Model model,
                            @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_CLASS, required = false) MemberClass loginMemberClass,
                            @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long trainerNo) {
        LessonResponseDTO registered = trainerOnlyService.registered(trainerNo);


        if (registered.getErrorMsg().equals("NotFound")) {
            model.addAttribute("lesson", null);
        } else {
            model.addAttribute("lesson", registered);
        }

        TrainerResponseDTO trainerResponseDTO = trainerService.findOneTrainer(trainerNo);
        model.addAttribute("trainer", trainerResponseDTO);
        return "trainerOnly/myLesson";
    }
    //트레이너 회원리스트
    //트레이너 페이지

    // 회원 불러오기
    @GetMapping("/mypage/memberList/{lessonNo}")
    public String memberList(@PathVariable Long lessonNo, Model model,
                             @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_CLASS, required = false) MemberClass loginMemberClass,
                             @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long trainerNo) {

        // 멤버 정보
        TrainerEnterRoomResponseDto trainerEnterRoomDto = trainerOnlyService.enrollMemList(lessonNo, trainerNo);
        model.addAttribute("trainerEnterRoom", trainerEnterRoomDto);

        return "trainerOnly/myMemberList";

    }

    // 룸 입장(없을 시 생성, 있을 시 상태 ACTIVE)
    @ResponseBody
    @PostMapping("/checkRoom")
    public TrainerRoomResponseDto checkRoom(@RequestBody TrainerEnterRoomRequestDto trainerEnterRoomRequestDto) {
        System.out.println("titleCode = " + trainerEnterRoomRequestDto.getTitleCode());
        System.out.println("enrollKey = " + trainerEnterRoomRequestDto.getEnrollKey());
        TrainerRoomResponseDto roomResponseDto
            = trainerOnlyService.createOrUptedaRoom(trainerEnterRoomRequestDto.getTitleCode(),
            trainerEnterRoomRequestDto.getEnrollKey());
        return roomResponseDto;

    }

    @GetMapping("/mypage/followed")
    public String followed(Model model, HttpSession session) {
        model.addAttribute("bannerTitle", "followed");
        TrainerResponseDTO trainer = trainerService.sessionT(session);
        List<MemberResponseDTO> followed = followService.followList(trainer.getTrainerNo());
        model.addAttribute("followed", followed);

        return "trainerOnly/followed";
    }


    @GetMapping("/mypage/liked")
    public String liked(Model model, HttpSession session, Long lessonNo) {
        try {
            model.addAttribute("bannerTitle", "liked");
            TrainerResponseDTO trainer = trainerService.sessionT(session);
            List<MemberResponseDTO> likedMembers = likeService.likedList(lessonNo);
            model.addAttribute("likedMembers", likedMembers);
        } catch (Exception e) {
            logger.error("TOC liked() 에러" + e.getMessage());
            e.printStackTrace();
        }
        return "trainerOnly/liked";
    }
}
