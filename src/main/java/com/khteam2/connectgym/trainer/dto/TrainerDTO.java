package com.khteam2.connectgym.trainer.dto;

import com.khteam2.connectgym.trainer.License;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDTO {

    private String trainerId;
    private String trainerPw;
    private String trainerName;
    private String trainerTel;
    private List<License> licenses;
    private String profileImg;
    private String trainerInfo;
    private LocalDate regDate;

}
