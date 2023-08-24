package com.khteam2.connectgym.common;

import com.khteam2.connectgym.member.interceptor.LoginCheckInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
            .order(1)
            // 아래 지정한 URL에 한해서 로그인 유무를 확인한다.
            .addPathPatterns("/order/**");
    }
}
