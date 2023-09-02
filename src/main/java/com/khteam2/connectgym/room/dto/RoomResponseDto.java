package com.khteam2.connectgym.room.dto;

import com.khteam2.connectgym.lesson.dto.LessonResponseDTO;
import com.khteam2.connectgym.room.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponseDto {

    private Long no;
    private String roomName;
    private String lessonTitle;

}
