package com.khteam2.connectgym.trainer;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class Licenses {

    @Id @GeneratedValue
    private Long no;

    @Column(name="license_img")
    private String licenseImg;

}
