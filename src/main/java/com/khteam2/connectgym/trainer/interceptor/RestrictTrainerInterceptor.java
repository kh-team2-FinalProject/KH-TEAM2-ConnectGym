package com.khteam2.connectgym.trainer.interceptor;

import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.member.MemberClass;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 트레이너 회원은 이용할 수 없게 하는 인터셉터
 */
@Component
public class RestrictTrainerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        MemberClass memberClass = (MemberClass) session.getAttribute(SessionConstant.LOGIN_MEMBER_CLASS);

        if (memberClass == MemberClass.TRAINER) {
            request.setAttribute("message", "트레이너 회원은 이용하실 수 없습니다.");
            request.setAttribute("returnBackPage", true);
            request.getRequestDispatcher("/error/error").forward(request, response);

            return false;
        }

        return true;
    }
}
