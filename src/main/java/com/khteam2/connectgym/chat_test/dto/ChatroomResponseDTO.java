package com.khteam2.connectgym.chat_test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatroomResponseDTO {

    private Long no;
    private Long memberNo;
    private Long trainerNo;

}
