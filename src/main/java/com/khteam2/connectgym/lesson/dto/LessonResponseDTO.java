package com.khteam2.connectgym.lesson.dto;

import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.trainer.Trainer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonResponseDTO {

    private Long no;
    private String title;
    private String titleCode;
    private Trainer trainer;
    private int price;
    private int category;
    private String lesson_info;
    private LocalDate start_date;
    private LocalDate end_date;
    private String lesson_img;
    private String errorMsg;

    public LessonResponseDTO(Lesson lesson) {
        this.no = lesson.getNo();
        this.title = lesson.getTitle();
        this.titleCode = lesson.getTitleCode();
        this.trainer = lesson.getTrainer();
        this.price = lesson.getPrice();
        this.category = lesson.getCategory();
        this.lesson_info = lesson.getLesson_info();
        this.start_date = lesson.getStart_date();
        this.end_date = lesson.getEnd_date();
        this.lesson_img = lesson.getLesson_img();
    }


}
