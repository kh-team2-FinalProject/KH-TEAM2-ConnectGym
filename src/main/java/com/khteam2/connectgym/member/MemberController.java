package com.khteam2.connectgym.member;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.member.dto.MemberDTO;
import javax.servlet.http.HttpSession;

import com.khteam2.connectgym.trainer.Trainer;
import com.khteam2.connectgym.trainer.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

@RequiredArgsConstructor
@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MailSendService mailService;

    @GetMapping(value = "/temp_login")
    public String tempLogin(
        Model model,
        @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_NO, required = false) Long loginMemberNo
    ) {
        if (loginMemberNo != null) {
            model.addAttribute("message", "이미 로그인되어 있는 상태입니다."
                + " 로그아웃 하려면 /temp_logout 으로 이동하면 됩니다.");
        }

        return "/content/tempLogin";
    }

    @PostMapping(value = "/temp_loginProcess")
    public String tempLoginProcess(HttpSession session, String userId, String userPassword) {
        long result = this.memberService.tempLoginProcess(userId, userPassword);

        if (result > 0) {
            session.setAttribute(SessionConstant.LOGIN_MEMBER_NO, result);
            return "redirect:/";
        }

        return "redirect:/temp_login";
    }

    @GetMapping(value = "/temp_logout")
    public String tempLogout(HttpSession session) {
        session.removeAttribute(SessionConstant.LOGIN_MEMBER_NO);

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

    //임시
    private  final TrainerRepository trainerRepository;
    @GetMapping("/myInfo")
    public String myInfo(Model model) {
        //배너타이틀
        model.addAttribute("bannerTitle", "my info");
        Trainer trainer = trainerRepository.findById(6L).orElse(null);

        model.addAttribute("trainer",trainer);
        return "mypage/myInfo";
    }


}
