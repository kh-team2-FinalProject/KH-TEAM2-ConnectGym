package com.khteam2.connectgym.lesson;


import com.khteam2.connectgym.room.Room;

import javax.persistence.*;
import java.util.List;

public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "lesson")
    private List<Room> roomList;




}
