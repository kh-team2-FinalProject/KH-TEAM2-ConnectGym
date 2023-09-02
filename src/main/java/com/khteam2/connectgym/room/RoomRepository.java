package com.khteam2.connectgym.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {

    @Query("select r from Room r where r.roomName=?1")
    Optional<Room> findByRoomName(String reqRoomName);
}
