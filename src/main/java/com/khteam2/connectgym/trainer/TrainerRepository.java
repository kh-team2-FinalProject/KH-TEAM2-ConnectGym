package com.khteam2.connectgym.trainer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {


    @Query("SELECT t FROM Trainer t WHERE t.trainerId = ?1")
    List<Trainer> findTrainerId(String id);

    Trainer findByTrainerId(String trainerId);

    @Query("SELECT t FROM Trainer t WHERE t.no = :trainerNo")
    Trainer findTrainerByLessonNo(@Param("trainerNo") Long trainerNo);


}
