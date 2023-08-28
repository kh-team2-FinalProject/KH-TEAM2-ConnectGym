package com.khteam2.connectgym.trainer;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khteam2.connectgym.enroll.EnrollDetail;
import com.khteam2.connectgym.lesson.Lesson;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity @Getter
@NoArgsConstructor
@Table(name = "trainers")
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private String trainerId;
    private String trainerPw;

    @Column(name="trainer_name")
    private String trainerName;

    private String trainerTel;

    @OneToMany
    @JoinColumn(name="license_no")
    private List<Licenses> licenses;

    private String profileImg;

    private String trainerInfo;

    private LocalDate regDate;

    @Builder
    public Trainer(String trainerId, String trainerPw, String trainerName, String trainerTel, String profileImg, String trainerInfo) {
        this.trainerId = trainerId;
        this.trainerPw = trainerPw;
        this.trainerName = trainerName;
        this.trainerTel = trainerTel;
        this.profileImg = profileImg;
        this.trainerInfo = trainerInfo;
    }
}



