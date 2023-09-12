package com.khteam2.connectgym.common;

import com.khteam2.connectgym.common.interceptor.LoginCheckInterceptor;
import com.khteam2.connectgym.member.interceptor.MemberOnlyInterceptor;
import com.khteam2.connectgym.trainer.interceptor.RestrictTrainerInterceptor;
import com.khteam2.connectgym.trainer.interceptor.TrainerOnlyInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final LoginCheckInterceptor loginCheckInterceptor;
    private final MemberOnlyInterceptor memberOnlyInterceptor;
    private final TrainerOnlyInterceptor trainerOnlyInterceptor;
    private final RestrictTrainerInterceptor restrictTrainerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 로그인 유무 확인
        registry.addInterceptor(this.loginCheckInterceptor)
            .order(1)
            // 아래 지정한 URL에 한해서 로그인 유무를 확인한다.
            .addPathPatterns(
                // 일반 회원 전용
                "/mypage/**",
                "/order/**",
                "/review/createReview",
                "/fooddiary",

                // 트레이너 회원 전용
                "/trainerOnly/**",
                "/createLesson",
                "/updateLesson/**"
            );

        // 일반 회원 전용
        registry.addInterceptor(this.memberOnlyInterceptor)
            .order(20)
            .addPathPatterns(
                "/mypage/**",
                "/order/**",
                "/review/createReview",
                "/fooddiary"
            );

        // 트레이너 회원 접근 제한
        registry.addInterceptor(this.restrictTrainerInterceptor)
            .order(22)
            .addPathPatterns(
                "/lessonList/**",
                "/trainerList/**",
                "/trainerDetail/**",
                "/lessonDetail/**"
            );

        // 트레이너 회원 전용
        registry.addInterceptor(this.trainerOnlyInterceptor)
            .order(24)
            .addPathPatterns(
                "/trainerOnly/**",
                "/createLesson",
                "/updateLesson/**"
            );
    }
}
