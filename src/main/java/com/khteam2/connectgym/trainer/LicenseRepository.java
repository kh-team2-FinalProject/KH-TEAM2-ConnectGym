package com.khteam2.connectgym.trainer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LicenseRepository extends JpaRepository<License, Long> {
    @Query("SELECT l FROM License l WHERE l.trainer.no = ?1")
    List<License> findAllTrainerNo(Long trainerNo);
}
