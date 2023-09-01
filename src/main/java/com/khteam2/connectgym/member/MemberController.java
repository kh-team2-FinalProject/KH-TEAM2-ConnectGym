package com.khteam2.connectgym.member;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.member.dto.MemberDTO;

import javax.servlet.http.HttpSession;

import com.khteam2.connectgym.member.dto.MemberResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final MailSendService mailService;

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


    //=====마이페이지=====
    private final MemberRepository memberRepository;

    @GetMapping("/mypage")
    public String myPage(){
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

}
