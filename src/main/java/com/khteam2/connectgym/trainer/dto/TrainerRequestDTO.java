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
    /*    private List<License> licenseList = new ArrayList<>();*/
    private String profileImg;
    private String infoTitle;
    private String infoContent;
    private String trainerEmail;


    //라이선스 추가
/*    public void addLicense(License licenses) {
        licenseList.add(licenses);
        licenses.setTrainer(this.toEntity());
    }*/

    public Trainer toEntity() {
        return Trainer.builder()
            .trainerId(trainerId)
            .trainerPw(trainerPw)
            .trainerName(trainerName)
            .trainerTel(trainerTel)
            /*.licenseList(licenseList)*/
            .profileImg(profileImg)
            .infoTitle(infoTitle)
            .infoContent(infoContent)
            .trainerEmail(trainerEmail)
            .build();
    }
}
