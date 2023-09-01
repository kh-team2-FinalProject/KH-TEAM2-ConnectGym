package com.khteam2.connectgym.lesson;


import com.khteam2.connectgym.trainer.Trainer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private String lesson_info;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start_date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end_date;

    private String lesson_img;

}
