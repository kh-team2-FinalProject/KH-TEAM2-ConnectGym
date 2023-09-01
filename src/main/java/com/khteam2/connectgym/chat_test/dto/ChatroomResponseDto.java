package com.khteam2.connectgym.chat_test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatroomResponseDto {
    private boolean success;
    private String message;
    //    private List<Lesson> lessonList;
    private String name;
    private String telNo;
    private Long price;
    private String orderNo;
}
