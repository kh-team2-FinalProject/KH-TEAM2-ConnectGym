package com.khteam2.connectgym.member.interceptor;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.member.MemberClass;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class MemberOnlyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        MemberClass memberClass = (MemberClass) session.getAttribute(SessionConstant.LOGIN_MEMBER_CLASS);

        if (memberClass != MemberClass.MEMBER) {
            request.setAttribute("message", "일반 회원만 이용하실 수 있습니다.");
            request.setAttribute("returnBackPage", true);
            request.getRequestDispatcher("/error/error").forward(request, response);

            return false;
        }

        return true;
    }
}
