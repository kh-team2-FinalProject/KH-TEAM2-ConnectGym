package com.khteam2.connectgym.chat_test;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class ChattingRoom {
    @Id
    @GeneratedValue
    private Long no;

    private Long userNo;

    private Long trainerNo;


}

