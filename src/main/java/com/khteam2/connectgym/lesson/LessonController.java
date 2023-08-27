package com.khteam2.connectgym.lesson;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @GetMapping(value = "/createLesson")
    public String createLesson(Model model) {
        model.addAttribute("lessonForm", new Lesson());
        return "content/createLesson";
    }

    @PostMapping("/createLesson")
    public String create(@ModelAttribute("lessonForm") Lesson lesson) {
        lessonService.createLesson(lesson);
        return "redirect:/content/createLesson";

    }

    @GetMapping(value = "/lessonDetail")
    public String lessonDetail(Lesson lesson) {
        return "content/lessonDetail";
    }
}

