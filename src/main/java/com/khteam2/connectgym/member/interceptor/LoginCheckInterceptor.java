package com.khteam2.connectgym.member.interceptor;

import com.khteam2.connectgym.common.SessionConstant;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 로그인이 되어있는지 확인하기 위한 인터셉터
 */
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        // session이 없거나 세션에서 가져온 값이 null일 경우 로그인 페이지로 이동한다.
        if (session == null || session.getAttribute(SessionConstant.LOGIN_MEMBER_NO) == null) {
            response.sendRedirect("/user/login");
            // 더 이상 진행하지 않도록 한다.
            return false;
        }

        // 로그인이 되어있으니 계속 진행한다.
        return true;
    }
}
