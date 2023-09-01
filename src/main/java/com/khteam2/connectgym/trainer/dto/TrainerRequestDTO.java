package com.khteam2.connectgym.trainer.dto;

import com.khteam2.connectgym.trainer.License;
import com.khteam2.connectgym.trainer.Trainer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerRequestDTO {

    private String trainerId;
    private String trainerPw;
    private String trainerName;
    private String trainerTel;
    private List<License> licenseList = new ArrayList<>();
    private String profileImg;
    private String trainerInfo;
    private String trainerEmail;


    //라이선스 추가
    public void addLicense(License licenses) {
        licenseList.add(licenses);
        licenses.setTrainer(this.toEntity());
    }

    public Trainer toEntity() {
        return Trainer.builder()
            .trainerId(trainerId)
            .trainerPw(trainerPw)
            .trainerName(trainerName)
            .trainerTel(trainerTel)
            .licenseList(licenseList)
            .profileImg(profileImg)
            .trainerInfo(trainerInfo)
            .trainerEmail(trainerEmail)
            .build();
    }
}
