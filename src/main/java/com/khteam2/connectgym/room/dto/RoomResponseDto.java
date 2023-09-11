package com.khteam2.connectgym.room.dto;

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
