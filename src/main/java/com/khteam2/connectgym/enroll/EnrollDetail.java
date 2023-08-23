package com.khteam2.connectgym.enroll;

import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.trainer.Trainer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Random;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="enroll_detail")
public class EnrollDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne
    @JoinColumn(name="user_no")
    private Member member;

    @ManyToOne
    @JoinColumn(name="lesson_no")
    private Lesson lesson;

    private String enroll_key;

    public EnrollDetail(Long no, Member member, Lesson lesson) {
        this.no = no;
        this.member = member;
        this.lesson = lesson;
        this.enroll_key = generateRandomString();
    }

    //룸키 난수 생성기
    public static String generateRandomString() {
       final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
       final int STRING_LENGTH = 12;

        Random random = new Random();
        StringBuilder sb = new StringBuilder(STRING_LENGTH);

        for (int i = 0; i < STRING_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
