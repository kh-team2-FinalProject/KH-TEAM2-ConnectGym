package com.khteam2.connectgym.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {

    @Query(value="select * from rooms where enroll_detail_no=?1",nativeQuery = true)
    Room findRoomKey(Long enrollNo);
}
