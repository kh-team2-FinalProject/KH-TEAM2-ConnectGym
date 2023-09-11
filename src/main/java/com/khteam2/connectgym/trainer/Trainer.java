package com.khteam2.connectgym.trainer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "trainers")
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private String trainerId;
    private String trainerPw;

    @Column(name = "trainer_name")
    private String trainerName;

    private String trainerTel;

    private String profileImg;

    private String infoTitle;
    private String infoContent;

    private LocalDate regDate;
    @Column(name = "trainer_email")
    private String trainerEmail;
}
