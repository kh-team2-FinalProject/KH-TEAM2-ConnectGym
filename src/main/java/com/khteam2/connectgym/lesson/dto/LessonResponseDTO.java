package com.khteam2.connectgym.lesson.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class LessonResponseDTO {

    private Long no;
    private String title;
    private String title_code;
    private String trainer_no;
    private String price;
    private String category;
    private String lesson_info;
    private Date start_date;
    private Date end_date;
    private String lesson_img;
}
