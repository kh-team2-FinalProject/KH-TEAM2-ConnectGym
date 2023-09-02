package com.khteam2.connectgym.member;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
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
    @Column(name = "user_tel")
    private String userTel;
    @Column(name = "user_address")
    private String userAddress;
    @Column(name = "user_email", unique = true, nullable = false)
    private String userEmail;
    @Column(name = "reg_date")
    @CreationTimestamp
    private LocalDateTime regDate;

}
