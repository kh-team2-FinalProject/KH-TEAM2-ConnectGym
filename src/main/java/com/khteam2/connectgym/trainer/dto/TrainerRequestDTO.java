package com.khteam2.connectgym.trainer.dto;

import com.khteam2.connectgym.trainer.Licenses;
import com.khteam2.connectgym.trainer.Trainer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TrainerRequestDTO {

    private String trainerId;
    private String trainerPw;
    private String trainerName;
    private String trainerTel;
//    private List<Licenses> licenses;
    private String profileImg;
    private String trainerInfo;

    @Builder
    public TrainerRequestDTO(String trainerId, String trainerPw, String trainerName, String trainerTel, String profileImg, String trainerInfo) {
        this.trainerId = trainerId;
        this.trainerPw = trainerPw;
        this.trainerName = trainerName;
        this.trainerTel = trainerTel;

        this.profileImg = profileImg;
        this.trainerInfo = trainerInfo;
    }

    public Trainer toEntity(){
        return Trainer.builder()
            .trainerId(trainerId)
            .trainerPw(trainerPw)
            .trainerName(trainerName)
            .trainerTel(trainerTel)
            .profileImg(profileImg)
            .trainerInfo(trainerInfo)
            .build();
    }
}
