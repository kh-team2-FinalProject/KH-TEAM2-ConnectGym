package com.khteam2.connectgym.room;

import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;
    private String roomKey;

    @ManyToOne
    @JoinColumn(name = "no") //테이블 컬럼명
    private Order order;




}
