package com.khteam2.connectgym.trainer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    @Query("SELECT t FROM Trainer t WHERE t.trainerId = ?1")
    List<Trainer> findTrainerId(String id);

    Trainer findByTrainerId(String trainerId);

    @Query("SELECT t FROM Trainer t WHERE t.no = :trainerNo")
    Trainer findTrainerByLessonNo(@Param("trainerNo") Long trainerNo);


    @Query(value = "SELECT t FROM Trainer t"
        + " WHERE t.trainerName LIKE CONCAT('%',TRIM(?1),'%')")
    List<Trainer> searchByTrainerName(String keyword);

    @Modifying
    @Query("UPDATE Trainer t SET t.infoTitle = :infoTitle WHERE t.no = :no ")
    void updateInfoTitle(@Param("infoTitle") String infoTitle, @Param("no") Long no);

    @Modifying
    @Query("UPDATE Trainer t SET t.infoContent = :infoContent WHERE t.no = :no ")
    void updateInfoContent(@Param("infoContent") String infoContent, @Param("no") Long no);

    @Modifying
    @Query("UPDATE Trainer t SET t.profileImg = :profileImg WHERE t.no = :no ")
    void updateProfileImg(@Param("profileImg") String profileImg, @Param("no") Long no);

    @Modifying
    @Query("UPDATE Trainer t SET t.trainerPw = :trainerPw WHERE t.no = :no")
    void updatePassword(String trainerPw, Long no);

}
