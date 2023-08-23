package com.khteam2.connectgym.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khteam2.connectgym.enroll.EnrollDetail;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @Column(name = "user_id", unique = true, nullable = false)
    private String userId;
    @Column(name = "user_pw", nullable = false)
    private String userPw;
    @Column(name = "user_name", nullable = false)
    private String userName;
    @Column(name = "user_tel", nullable = false)
    private String userTel;
    @Column(name = "user_address")
    private String userAddress;
    @Column(name = "user_email")
    private String userEmail;

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<EnrollDetail> lessonList;

}
