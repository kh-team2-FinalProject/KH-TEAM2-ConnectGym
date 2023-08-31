package com.khteam2.connectgym.trainer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerResponseDTO {

    private Long trainerNo;
    private String trainerId;
    private String trainerName;
    private String trainerTel;
//    private List<License> licenseList;
    private String profileImg;
    private String trainerInfo;


}
