package com.khteam2.connectgym.member;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.member.dto.MemberDTO;
import com.khteam2.connectgym.member.dto.MemberResponseDTO;
import com.khteam2.connectgym.trainer.Trainer;
import com.khteam2.connectgym.trainer.TrainerRepository;
import com.khteam2.connectgym.trainer.TrainerService;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

@RequiredArgsConstructor
@Controller
@Slf4j
public class MemberController {

    private final TrainerRepository trainerRepository;
    private final TrainerService trainerService;
    private final MemberService memberService;
    private final MailSendService mailService;
    //=====마이페이지=====
    private final MemberRepository memberRepository;

    @GetMapping(value = "/user/login")
    public String tempLogin(
        Model model,
        @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long loginMemberNo
    ) {
        if (loginMemberNo != null) {
            model.addAttribute("message", "이미 로그인되어 있는 상태입니다."
                + " 로그아웃 하려면 /temp_logout 으로 이동하면 됩니다.");
        }

        return "/content/login";
    }

    @PostMapping(value = "/user/loginProcess")
    public String tempLoginProcess(HttpSession session, String userId, String userPassword) {
        long result = this.memberService.loginProcess(userId, userPassword);

        if (result > 0) {
            session.setAttribute(SessionConstant.LOGIN_MEMBER_NO, result);

            return "redirect:/";
        }

        return "redirect:/user/login";
    }

    @GetMapping(value = "/user/logout")
    public String tempLogout(HttpSession session) {
        session.removeAttribute(SessionConstant.LOGIN_MEMBER_NO);
        session.removeAttribute(SessionConstant.LOGIN_MEMBER_CLASS);

        return "redirect:/";
    }

    @GetMapping(value = "/temp_join")
    public String tempJoin() {
        return "/content/tempJoin";
    }

    // 회원가입 버튼 클릭 시 실행
    @PostMapping("/temp_joinProcess")
    public String saveUser(MemberDTO memberDTO) {
        memberService.createMember(memberDTO);
        return "content/main";
    }

    //    // email 인증시 사용
    @GetMapping("/mailCheck")
    @ResponseBody
    public String mailCheck(String email) {
        System.out.println("email 들어오는지 체크중" + email);
        return mailService.joinEmail(email);
    }

    @GetMapping("/mypage")
    public String myPage(
        @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long loginMemberNo,
        @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_CLASS, required = false) MemberClass loginMemberClass) {
        if (SessionConstant.LOGIN_MEMBER_CLASS == null || loginMemberNo == null) {
            log.info("로그인되어 있지 않음");
        } else if (loginMemberClass == MemberClass.MEMBER) {
            log.info("일반 회원 로그인됨");
            Member member = this.memberRepository.findById(loginMemberNo).orElse(null);
        } else if (loginMemberClass == MemberClass.TRAINER) {
            log.info("트레이너 회원 로그인됨");
            Trainer trainer = this.trainerRepository.findById(loginMemberNo).orElse(null);
        }

        return "redirect:/mypage/myDashboard";
    }

    // 1) 대시보드
    @GetMapping("/mypage/myDashboard")
    public String myDashboard(Model model) {
        //배너타이틀
        model.addAttribute("bannerTitle", "my dashboard");
        Member member = memberRepository.findById(1L).orElse(null);

        model.addAttribute("member", member);
        return "mypage/dashboard";
    }

    // 2) 내 수강목록
    @GetMapping("/mypage/myLessonList")
    public String myLesson(Model model) {
        //배너타이틀
        model.addAttribute("bannerTitle", "MY LESSON");
        // (삭제예정)세션에서 꺼내오기 못해서 고정으로 테스트 중
        MemberResponseDTO member = memberService.findOneMember(1L);
        model.addAttribute("member", member);
        System.out.println("member = " + member.getUserName());
        System.out.println("마이레슨리스트 컨트롤러 호출");
        return "mypage/mylessonlist";
    }


    @GetMapping(value = "/mypage/convertToTrainerAccount")
    public String convertAccount(Model model) {
        model.addAttribute("bannerTitle", "CONVERT TO TRAINER ACCOUNT");
        return "mypage/convertToTrainerAccount";
    }


    @GetMapping("/mypage/myInfo")
    public String myInfo(Model model, HttpSession session) {
        //배너타이틀
        model.addAttribute("bannerTitle", "my info");

        Long sessionUserNo = (Long) session.getAttribute(SessionConstant.LOGIN_MEMBER_NO);
        MemberResponseDTO member = memberService.findOneMember(sessionUserNo);

        model.addAttribute("member", member);

        return "mypage/myInfo";
    }

    @GetMapping("/mypage/update")
    public String update() {
        return "/mypage/update";
    }

    @PostMapping(value = "/mypage/updateProcess")
    public String updateProcess(MemberDTO memberDTO) {

        // 회원정보 수정 버튼 누르면 실행되는 컨트롤러
        // 버튼 클릭 시 회원정보 수정해주는 서비스 함수 실행
        memberService.updateMember(memberDTO);

        return "redirect:/mypage/myInfo";
    }


    //  로컬호스트일 때의 urlmapping
    @RequestMapping(value = "/connectgym", method = RequestMethod.GET)
    public String kakaoLogin(@RequestParam(value = "code", required = false) String code,
        HttpSession session) {

        // 인가코드 받는 부분 // 출력 테스트
        System.out.println("###########" + code);

        // 토큰 받는 부분
        String access_Token = memberService.getAccessToken(code);
        System.out.println(access_Token);

        // 실제 정보를 출력하는 메서드 호출하기
        HashMap<String, Object> userInfo = memberService.getUserInfo(access_Token);

        // 카카오 정보를 이용하여 users 테이블의 email과 비교하여 회원정보가 있는지 확인하기
        String kakaoemail = (String) userInfo.get("email");
        HashMap<String, Object> m = memberService.findMemberByEmail(kakaoemail);
        HashMap<String, Object> t = trainerService.findTrainerByEmail(kakaoemail);

        // 출력 테스트용
        System.out.println("############" + kakaoemail);

        // 응답받아오면 카카오 정보 비교하여 있으면 main으로 없으면 회원가입으로 return
        if (m == null && t == null) {
            session.setAttribute("kakaoEmail", kakaoemail);

            // 휴대폰 번호 받아올 수 있을 때 그 정보도 추가해야함.

            return "redirect:/temp_join";
        } else {
            System.out.println("==============member: " + m);
            System.out.println("==============trainer: " + t);
            if (m != null) {
                // users table에 있을 경우 session 저장
                session.setAttribute(SessionConstant.LOGIN_MEMBER_NO, m.get("user_no"));
                session.setAttribute(SessionConstant.LOGIN_MEMBER_CLASS, MemberClass.MEMBER);
            }
            if (t != null) {
                // trainer table에 있을 경우 session 저장
                session.setAttribute(SessionConstant.LOGIN_MEMBER_NO, t.get("trainer_no"));
                session.setAttribute(SessionConstant.LOGIN_MEMBER_CLASS, MemberClass.TRAINER);
            }

            return "redirect:/";
        }
    }


}
