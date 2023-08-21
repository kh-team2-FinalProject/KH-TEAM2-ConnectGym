package com.khteam2.connectgym.enroll;

import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.trainer.Trainer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name="enroll_detail")
public class EnrollDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_no")
    private Member member;

    @ManyToOne
    @JoinColumn(name="lesson_no")
    private Lesson lesson;

    private String enroll_key;

}
