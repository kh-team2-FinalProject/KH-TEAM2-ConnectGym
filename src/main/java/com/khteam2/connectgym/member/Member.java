package com.khteam2.connectgym.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khteam2.connectgym.enroll.EnrollDetail;
import com.khteam2.connectgym.lesson.Lesson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity @Getter
@NoArgsConstructor
@Table(name="users")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long id;


    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<EnrollDetail> lessonList;


}
