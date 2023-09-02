package com.khteam2.connectgym.room;

import com.khteam2.connectgym.order.OrderDetail;
import com.khteam2.connectgym.room.dto.RoomStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "gym_rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;


    @OneToOne
    @JoinColumn(name="order_detail_no")
    private OrderDetail orderDetail;

    private String roomName;

    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;

/*
    @OneToOne
    @JoinColumn(name = "enroll_detail_no") //테이블 컬럼명
    private EnrollDetail enrollDetail;
*/




}
