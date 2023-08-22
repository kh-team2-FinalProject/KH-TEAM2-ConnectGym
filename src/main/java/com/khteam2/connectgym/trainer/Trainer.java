package com.khteam2.connectgym.trainer;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khteam2.connectgym.enroll.EnrollDetail;
import com.khteam2.connectgym.lesson.Lesson;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor
@Table(name = "trainers")
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(name="trainer_name")
    private String trainerName;


    @OneToOne(mappedBy = "trainer")
    @JsonIgnore
    private Lesson lesson;

}



