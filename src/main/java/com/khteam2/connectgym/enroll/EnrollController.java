package com.khteam2.connectgym.enroll;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EnrollController {

    private final EnrollService enrollService;


    //url 사이에 멤버id를 넣어야하나?
    @GetMapping("/api/lessonlist")
    public List<EnrollDetail> lessonlist() {
        //원래는 로그인한 세션의 멤버 id를 넣어줘야 함
        return enrollService.memLessonList(3L);
    }

}
