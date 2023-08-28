package com.khteam2.connectgym.lesson;


import com.khteam2.connectgym.lesson.dto.LessonResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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
    public String createLesson(Model model, LessonResponseDTO LessonResponseDTO) {
        //배너타이틀
        model.addAttribute("bannerTitle", "create lesson");


        //service save() 호출
        lessonService.createLesson(LessonResponseDTO);

        return "detailOrCrud/createComplete";
    }


    @GetMapping(value = "/lessonDetail")
    public String lessonDetail(Model model, Lesson lesson) {
        //배너타이틀
        model.addAttribute("bannerTitle", "lesson detail");
        return "detailOrCrud/lessonDetail";
    }


    //레슨 다 가져오기
    @GetMapping("/lesson-list")
    public String lessonList(Model model) {
        List<Lesson> lessonList = lessonService.getAllLessons();
        model.addAttribute("lessonList", lessonList);
        return "lesson/lessonCategory";
    }


    //레슨 한 개만 불러오기
//    @GetMapping("/lesson/{lessonNo}")
//    public String viewLesson(@PathVariable Long lessonNo, Model model){
//        Lesson lesson = lessonService.getLesson(lessonNo);
//        model.addAttribute("lesson", lesson);
//        return "lesson/view";
//    }

}

