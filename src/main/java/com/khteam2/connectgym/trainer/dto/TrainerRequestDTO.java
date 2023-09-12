package com.khteam2.connectgym.trainer.dto;

import com.khteam2.connectgym.trainer.Trainer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerRequestDTO {
    private Long no;
    private String trainerId;
    private String trainerPw;
    private String trainerName;
    private String trainerTel;
    private String profileImg;
    private String infoTitle;
    private String infoContent;
    private String trainerEmail;

    public Trainer toEntity() {
        return Trainer.builder()
            .no(no)
            .trainerId(trainerId)
            .trainerPw(trainerPw)
            .trainerName(trainerName)
            .trainerTel(trainerTel)
            .profileImg(profileImg)
            .infoTitle(infoTitle)
            .infoContent(infoContent)
            .trainerEmail(trainerEmail)
            .build();
    }
}
