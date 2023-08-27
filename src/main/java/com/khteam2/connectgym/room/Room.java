package com.khteam2.connectgym.room;

import com.khteam2.connectgym.enroll.EnrollDetail;
import com.khteam2.connectgym.room.dto.RoomStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(name="room_key")
    private String roomKey;

    @Column(name="room_status")
    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;

    @OneToOne
    @JoinColumn(name = "enroll_detail_no") //테이블 컬럼명
    private EnrollDetail enrollDetail;




}
