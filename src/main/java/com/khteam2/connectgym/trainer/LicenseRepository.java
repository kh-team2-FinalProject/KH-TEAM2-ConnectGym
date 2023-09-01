package com.khteam2.connectgym.trainer;

import com.khteam2.connectgym.lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LicenseRepository extends JpaRepository<License, Long>  {

    @Query("select l from License l where l.trainer.no=?1")
    List<License> findAllTrainerNo(Long trainerNo);

}
