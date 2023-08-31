package com.khteam2.connectgym.trainer;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Builder
@Getter @Setter
@AllArgsConstructor
@Table(name="licenses")
public class License {

    @Id @GeneratedValue
    private Long no;

    @Column(name="license_img")
    private String licenseImg;


    @ManyToOne
    @JoinColumn(name="trainer_no")
    private Trainer trainer;


}
