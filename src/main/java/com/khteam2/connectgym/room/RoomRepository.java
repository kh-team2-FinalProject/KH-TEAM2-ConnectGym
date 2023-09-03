package com.khteam2.connectgym.room;

import com.khteam2.connectgym.room.dto.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface RoomRepository extends JpaRepository<Room,Long> {

    @Modifying
    @Query("update Room r set r.roomStatus = ?1 where r.no = ?2")
    int updateRoomStatus(RoomStatus roomStatus, Long roomNo);


    @Query("select r from Room r where r.roomName=?1")
    Optional<Room> findByRoomName(String reqRoomName);
}
