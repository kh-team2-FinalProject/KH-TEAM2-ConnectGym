package com.khteam2.connectgym.room.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data @NoArgsConstructor
@ToString
public class RoomRequest {
    private String roomName;
    private String roomKey;
//    private String roomToken;


}
