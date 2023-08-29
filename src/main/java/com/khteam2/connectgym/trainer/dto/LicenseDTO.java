package com.khteam2.connectgym.trainer.dto;

import com.khteam2.connectgym.trainer.Licenses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LicenseDTO {
    private String licenseImg;

    public Licenses toEntity(){
        return Licenses.builder()
            .licenseImg(licenseImg)
            .build();
    }

}
