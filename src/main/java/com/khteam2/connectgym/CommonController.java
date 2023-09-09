package com.khteam2.connectgym;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.follow.FollowService;
import com.khteam2.connectgym.member.MemberClass;
import com.khteam2.connectgym.member.MemberService;
import com.khteam2.connectgym.member.dto.MemberResponseDTO;
import com.khteam2.connectgym.review.ReviewService;
import com.khteam2.connectgym.review.dto.ReviewResponseDto;
import com.khteam2.connectgym.trainer.TrainerService;
import com.khteam2.connectgym.trainer.dto.TrainerResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class CommonController {

    private final MemberService memberService;
    private final FollowService followService;
    private final TrainerService trainerService;
    private final ReviewService reviewService;
    Logger logger = LoggerFactory.getLogger(CommonController.class);

    //*******************************************
    //메인
    @GetMapping("/")
    public String welcomeOrIndex(Model model, HttpSession session) {
        try {
            logger.error("welcomeOrIndex() 호출");

            MemberClass userRole = (MemberClass) session.getAttribute(SessionConstant.LOGIN_MEMBER_CLASS);

            if (userRole != null) {
                if (userRole.equals(MemberClass.MEMBER)) {
                    MemberResponseDTO member = memberService.sessionMem(session);
                    String userId = member.getUserId();
                    model.addAttribute("loginMemberId", userId);
                    model.addAttribute("memberClass", "member");
                } else if (userRole.equals(MemberClass.TRAINER)) {
                    TrainerResponseDTO trainerResponseDTO = trainerService.sessionT(session);
                    String trainerId = trainerResponseDTO.getTrainerId();
                    model.addAttribute("loginMemberId", trainerId);
                    model.addAttribute("memberClass", "trainer");
                }
            } else {
                model.addAttribute("errorMsg", "로그인 정보가 없습니다.");
            }

            List<ReviewResponseDto> top3Reviews = reviewService.top3Review();
            model.addAttribute("reviews", top3Reviews);

        } catch (Exception e) {
            logger.error("welcomeOrIndex() 에러");
        }

        return "content/main";
    }

    //팔로일 테스트화면
    @GetMapping("/mypage/myFollowingTest")
    public String myFolloing(Model model, HttpSession session) {

        //배너타이틀
        model.addAttribute("bannerTitle", "following");

        MemberResponseDTO member = memberService.sessionMem(session);

        List<TrainerResponseDTO> following = followService.followingList(member.getNo());

        model.addAttribute("following", following);
        model.addAttribute("follwStatus", "true");

        return "mypage/following_test2";
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
//    @GetMapping("/")
//    public String welcome(Model model, HttpSession session) {
//        String userRole = (String) session.getAttribute(SessionConstant.LOGIN_MEMBER_CLASS);
//
//        if ("MEMBER".equals(userRole)) {
//            MemberResponseDTO member = memberService.sessionMem(session);
//            String userId = member.getUserId();
//            model.addAttribute("userId", userId);
//
//        } else if ("TRAINER".equals(userRole)) {
//            TrainerResponseDTO trainerResponseDTO = trainerService.sessionT(session);
//            String trainerId = trainerResponseDTO.getTrainerId();
//            model.addAttribute("trainerId", trainerId);
//        }
//        return "main";
//    }
//}
