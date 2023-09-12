package com.khteam2.connectgym.chat_test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatroomRequestDTO {
    Long memberNo;
    Long trainerNo;
}
