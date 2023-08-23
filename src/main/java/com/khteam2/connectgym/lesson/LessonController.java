package com.khteam2.connectgym.lesson;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @GetMapping(value = "/createLesson")
    public String createLesson(Lesson lesson) {
        return "content/createLesson";
    }
}
