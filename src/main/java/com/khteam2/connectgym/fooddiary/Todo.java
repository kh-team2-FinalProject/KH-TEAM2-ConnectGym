package com.khteam2.connectgym.fooddiary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Data
@AllArgsConstructor
public class Todo {
    private String date;
    private String task;
}
