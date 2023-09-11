package com.khteam2.connectgym.like;

import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "likes", uniqueConstraints = {
    @UniqueConstraint(name = "uk_like_from_to", columnNames = {"user_no", "lesson_no"})
})
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "lesson_no")
    private Lesson lesson;
}
