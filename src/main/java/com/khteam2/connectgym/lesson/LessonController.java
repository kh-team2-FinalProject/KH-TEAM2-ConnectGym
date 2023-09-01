package com.khteam2.connectgym.lesson;


import com.khteam2.connectgym.lesson.dto.LessonRequestDTO;
import com.khteam2.connectgym.trainer.dto.TrainerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @GetMapping(value = "/createLesson")
    public String createLesson(Model model) {

        //배너타이틀
        model.addAttribute("bannerTitle", "create");

        //폼 호출
        model.addAttribute("lessonForm", new Lesson());

        return "detailOrCrud/createLesson";
    }

    @PostMapping(value = "/createLesson")
    public String createLesson(Model model, LessonRequestDTO lessonRequestDTO,
                               @RequestParam("lessonImgFile") MultipartFile file) {

        //배너타이틀
        model.addAttribute("bannerTitle", "create lesson");

        System.out.println("lessonRequestDTO = " + lessonRequestDTO.getStart_date());
        //service save() 호출
        lessonService.createLesson(lessonRequestDTO, file);

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
        model.addAttribute("bannerTitle", "lessons");

        List<Lesson> lessonList = lessonService.getAllLessons();
        model.addAttribute("lessonList", lessonList);
        return "lesson/lessonCategory";
    }


    //레슨 한 개만 불러오기
    @GetMapping("/lessonDetail/{lessonNo}")
    public String viewLesson(@PathVariable Long lessonNo, Model model) {
        model.addAttribute("bannerTitle", "lesson details");

        Lesson lesson = lessonService.getLessonById(lessonNo);

        TrainerResponseDTO trainerLessonResponseDTO = TrainerResponseDTO.builder()
            .trainerNo(lesson.getTrainer().getNo())
            .trainerId(lesson.getTrainer().getTrainerId())
            .trainerName(lesson.getTrainer().getTrainerName())
            .profileImg(lesson.getTrainer().getProfileImg())
            .infoTitle(lesson.getTrainer().getInfoTitle())
            .infoContent(lesson.getTrainer().getInfoContent())
            .build();


        model.addAttribute("trainerInfo", trainerLessonResponseDTO);
        model.addAttribute("lesson", lesson);

        return "detailOrCrud/lessonDetail";
    }


}

