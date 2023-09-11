package com.khteam2.connectgym.trainer.dto;


import com.khteam2.connectgym.room.dto.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerRoomResponseDto {
    private Long no;
    private String roomName;
    private RoomStatus roomStatus;
}
