package com.khteam2.connectgym.dietlist.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpenDataHeaderDto {
    private String resultCode;
    private String resultMsg; // 메세지
}
