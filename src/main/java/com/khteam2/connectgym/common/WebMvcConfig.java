package com.khteam2.connectgym.common;

import com.khteam2.connectgym.common.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.loginCheckInterceptor)
            .order(1)
            // 아래 지정한 URL에 한해서 로그인 유무를 확인한다.
            .addPathPatterns("/order/**", "/mypage/**");
    }
}
