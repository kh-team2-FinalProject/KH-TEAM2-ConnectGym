package com.khteam2.connectgym.member;

import com.khteam2.connectgym.common.SessionConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;

@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;

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
}
