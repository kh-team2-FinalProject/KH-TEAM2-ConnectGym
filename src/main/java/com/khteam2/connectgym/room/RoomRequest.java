package com.khteam2.connectgym.room;

import lombok.Data;

@Data
public class RoomRequest {

    private String roomName;

    private Long routineId;
    private String creator;
}
