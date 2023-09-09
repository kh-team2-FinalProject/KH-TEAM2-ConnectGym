package com.khteam2.connectgym.enroll.dto;


import com.khteam2.connectgym.lesson.dto.LessonResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollResponseDto {

    private LessonResponseDTO lesson;
    private String lessonTitleCode;
    private Long enrollKey;
    private String errorMsg;

}
