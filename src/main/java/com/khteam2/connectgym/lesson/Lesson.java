package com.khteam2.connectgym.lesson;


import com.khteam2.connectgym.room.Room;
import com.khteam2.connectgym.trainer.Trainer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity @Getter
@NoArgsConstructor
@Table(name="lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long id;

    private String title;

    @OneToOne
    @JoinColumn(name="trainer_no")
    private Trainer trainer;

    private int price;
    private int category;
    private String img_src;
    private String lesson_info;
    private LocalDate start_date;
    private LocalDate end_date;




}
