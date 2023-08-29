package com.khteam2.connectgym.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khteam2.connectgym.enroll.EnrollDetail;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @Column(name = "reg_date")
    @CreationTimestamp
    private LocalDateTime regDate;
    private String provider; //어디 api에서 왔는가 ex)구글 네이버등등
    private String providerId;//api 생성고유번호

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<EnrollDetail> lessonList;

}
