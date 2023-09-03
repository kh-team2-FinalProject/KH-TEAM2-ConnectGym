package com.khteam2.connectgym.trainer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {


    @Query("select t from Trainer t where t.trainerId = ?1")
    List<Trainer> findTrainerId(String id);

    Trainer findByTrainerId(String trainerId);

}
