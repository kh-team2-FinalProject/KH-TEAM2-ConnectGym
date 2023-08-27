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

        //배너타이틀
        model.addAttribute("bannerTitle", "create lesson");
        //폼 호출
        model.addAttribute("lessonForm", new Lesson());
        return "detailOrCrud/createLesson";
    }

    @PostMapping(value = "/createLesson")
    public String createLesson(Model model, Lesson lesson) {
        //배너타이틀
        model.addAttribute("bannerTitle", "create lesson");

        //service save() 호출

        return "detailOrCrud/createComplete";

    }

    @PostMapping("/createLesson")
    public String create(@ModelAttribute("lessonForm") Lesson lesson) {
        lessonService.createLesson(lesson);
        return "redirect:/content/createLesson";

    }

    @GetMapping(value = "/lessonDetail")
    public String lessonDetail(Model model, Lesson lesson) {
        //배너타이틀
        model.addAttribute("bannerTitle", "lesson detail");
        return "detailOrCrud/lessonDetail";
    }


}

