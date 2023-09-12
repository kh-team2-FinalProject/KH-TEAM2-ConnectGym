package com.khteam2.connectgym.room;

import com.khteam2.connectgym.room.dto.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Modifying
    @Query("UPDATE Room r SET r.roomStatus = ?1 WHERE r.no = ?2")
    int updateRoomStatus(RoomStatus roomStatus, Long roomNo);

    @Query("SELECT r FROM Room r WHERE r.roomName = ?1")
    Optional<Room> findByRoomName(String reqRoomName);
}
