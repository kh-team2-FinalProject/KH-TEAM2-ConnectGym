package com.khteam2.connectgym.lesson.dto;


import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.trainer.Trainer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonRequestDTO {

    private String title;
    private String titleCode;
    private Trainer trainer;
    private int price;
    private int category;
    private String lesson_info;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start_date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end_date;
    private String lesson_img;

    public Lesson toEntity() {

       return Lesson.builder()
            .title(title)
            .titleCode(titleCode)
            .trainer(trainer)
            .price(price)
            .category(category)
            .lesson_info(lesson_info)
            .start_date(start_date)
            .end_date(end_date)
            .lesson_img(lesson_img)
            .build();
    }

}
