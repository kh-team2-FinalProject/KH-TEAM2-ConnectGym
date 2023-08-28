package com.khteam2.connectgym.lesson;


import com.khteam2.connectgym.trainer.Trainer;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Getter
@NoArgsConstructor
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private String title;

    @Column(name = "title_code")
    private String titleCode;

    @OneToOne
    @JoinColumn(name = "trainer_no")
    private Trainer trainer;

    private int price;
    private int category;
    /*private String img_src;*/
    private String lesson_info;
    private LocalDate start_date;
    private LocalDate end_date;
    private String lesson_img;


}
