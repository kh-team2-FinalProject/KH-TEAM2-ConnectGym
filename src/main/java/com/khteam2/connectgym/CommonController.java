package com.khteam2.connectgym;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.member.MemberClass;
import com.khteam2.connectgym.member.MemberService;
import com.khteam2.connectgym.member.dto.MemberResponseDto;
import com.khteam2.connectgym.order.OrderDetailService;
import com.khteam2.connectgym.review.ReviewService;
import com.khteam2.connectgym.review.dto.ReviewResponseDto;
import com.khteam2.connectgym.trainer.TrainerService;
import com.khteam2.connectgym.trainer.dto.TrainerResponseDto;
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
    private final TrainerService trainerService;
    private final OrderDetailService orderDetailService;
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
                    MemberResponseDto member = memberService.sessionMem(session);
                    String userId = member.getUserId();
                    model.addAttribute("loginMemberId", userId);
                    model.addAttribute("memberClass", "member");
                } else if (userRole.equals(MemberClass.TRAINER)) {
                    TrainerResponseDto trainerResponseDTO = trainerService.sessionT(session);
                    String trainerId = trainerResponseDTO.getTrainerId();
                    model.addAttribute("loginMemberId", trainerId);
                    model.addAttribute("memberClass", "trainer");
                }
            } else {
                model.addAttribute("errorMsg", "로그인 정보가 없습니다.");
            }

            //메인 메뉴 TOP3 트레이너
            List<TrainerResponseDto> trainerList = orderDetailService.findTop3Trainer();
            model.addAttribute("trainerList", trainerList);

            //메인 메뉴 TOP3 리뷰
            List<ReviewResponseDto> top3Reviews = reviewService.top3Review();
            model.addAttribute("reviews", top3Reviews);

        } catch (Exception e) {
            logger.error("welcomeOrIndex() 에러");
        }

        return "content/main";
    }


    //레슨 페이지 내 메뉴 이동
    @GetMapping("/lesson")
    public String lesson() {
        return "redirect:/lesson/health";
    }

    @GetMapping("/mypage/messages")
    public String chattingRoomList(Model model) {
        //배너타이틀
        model.addAttribute("bannerTitle", "MY CHATTIING");
        MemberResponseDto member = memberService.findOneMember(1L);
        model.addAttribute("member", member);
        System.out.println("member = " + member.getUserName());
        System.out.println("마이레슨리스트 컨트롤러 호출");

        return "mypage/messages";
    }

    @GetMapping("/fooddiary")
    public String fooddiary() {
        return "redirect:/fooddiary/calendar";
    }

    @GetMapping("/error/error")
    public String errorPage() {
        return "error/error";
    }
}
