package com.khteam2.connectgym.dietlist;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FoodTime {
    BREAKFAST(1, "아침"),
    LUNCH(2, "점심"),
    DINNER(3, "저녁"),
    SNACK(4, "간식");

    private int no;
    private String name;
}
