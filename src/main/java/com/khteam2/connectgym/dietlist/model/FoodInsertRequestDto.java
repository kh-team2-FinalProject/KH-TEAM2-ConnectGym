package com.khteam2.connectgym.dietlist.model;

import com.khteam2.connectgym.dietlist.FoodTime;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FoodInsertRequestDto {
    private Long selectedKey;
    private FoodTime foodTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
}
