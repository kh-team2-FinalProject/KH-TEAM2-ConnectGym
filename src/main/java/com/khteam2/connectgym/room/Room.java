package com.khteam2.connectgym.room;

import com.khteam2.connectgym.order.OrderDetail;
import com.khteam2.connectgym.room.dto.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "gym_rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @OneToOne
    @JoinColumn(name = "order_detail_no")
    private OrderDetail orderDetail;

    private String roomName;

    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;
}
