package com.khteam2.connectgym.trainer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.trainer.Licenses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    private List<Licenses> licenses;
    private String profileImg;
    private String trainerInfo;
    private LocalDate regDate;

}
