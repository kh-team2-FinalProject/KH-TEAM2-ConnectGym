package com.khteam2.connectgym.trainer;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Builder
@Getter @Setter
@AllArgsConstructor
public class Licenses {

    @Id @GeneratedValue
    private Long no;

    @Column(name="license_img")
    private String licenseImg;


    @ManyToOne
    @JoinColumn(name="trainer_no")
    private Trainer trainer;


}
