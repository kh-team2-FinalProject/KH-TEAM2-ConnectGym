package com.khteam2.connectgym.trainer;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "trainer")
    @JsonIgnore
    private List<License> licenseList;

    private String profileImg;

    private String trainerInfo;

    private LocalDate regDate;





}



