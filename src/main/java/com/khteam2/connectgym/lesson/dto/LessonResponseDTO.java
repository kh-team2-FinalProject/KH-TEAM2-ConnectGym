package com.khteam2.connectgym.lesson.dto;

import com.khteam2.connectgym.trainer.Trainer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
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
}
