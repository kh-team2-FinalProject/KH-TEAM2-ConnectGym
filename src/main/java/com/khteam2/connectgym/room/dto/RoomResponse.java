package com.khteam2.connectgym.room.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class RoomResponse {

    private String roomName;
    private String roomKey;
    private String roomToken;

    @Builder
    public RoomResponse(String roomName, String roomKey, String roomToken) {
        this.roomName = roomName;
        this.roomKey = roomKey;
        this.roomToken = roomToken;
    }
}
