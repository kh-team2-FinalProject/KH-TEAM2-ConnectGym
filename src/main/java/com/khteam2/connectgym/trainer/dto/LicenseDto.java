package com.khteam2.connectgym.trainer.dto;

import com.khteam2.connectgym.trainer.License;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LicenseDTO {
    private String licenseImg;

    public License toEntity() {
        return License.builder()
            .licenseImg(licenseImg)
            .build();
    }
}
