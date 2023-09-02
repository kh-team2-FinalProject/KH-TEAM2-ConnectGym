package com.khteam2.connectgym.trainer.dto;

import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.trainer.License;
import com.khteam2.connectgym.trainer.Trainer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerResponseDTO {

    private Long trainerNo;
    private String trainerId;
    private String trainerName;
    private String trainerTel;
    private String profileImg;
    private String infoTitle;
    private String infoContent;
    private String trainerEmail;

    //트레이너 상세에서만 받음
    private List<License> licenses;
    private Long lessonNo;

    public TrainerResponseDTO(Trainer entity) {
            this.trainerNo = entity.getNo();
            this.trainerId = entity.getTrainerId();
            this.trainerName = entity.getTrainerName();
            this.trainerTel = entity.getTrainerTel();
            this.profileImg = entity.getProfileImg();
            this.infoTitle = entity.getInfoTitle();
            this.infoContent = entity.getInfoContent();
            this.trainerEmail = entity.getTrainerEmail();
    }

}
