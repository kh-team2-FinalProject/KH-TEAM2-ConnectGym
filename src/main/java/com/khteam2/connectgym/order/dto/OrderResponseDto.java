package com.khteam2.connectgym.order.dto;

import com.khteam2.connectgym.lesson.Lesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private boolean success;
    private String message;
    private List<Lesson> lessonList;
    private String name;
    private String telNo;
    private Long price;
    private String orderNo;
}
