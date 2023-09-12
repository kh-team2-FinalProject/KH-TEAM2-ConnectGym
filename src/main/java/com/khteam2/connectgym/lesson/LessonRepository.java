package com.khteam2.connectgym.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Query(value = "SELECT l FROM Member m, Order o, OrderDetail od"
        + " JOIN od.lesson l"
        + " WHERE m = o.member"
        + " AND od.order = o"
        + " AND m.no = :memberNo"
        + " AND l.no IN (:lessonNoList)")
    List<Lesson> findAllByMemberNoAndLessonNoList(@Param("memberNo") Long memberNo, @Param("lessonNoList") List<Long> lessonNoList);

    @Query(value = "SELECT l FROM OrderProgress op, OrderProgressDetail opd"
        + " JOIN opd.lesson l"
        + " WHERE opd.orderProgress = op"
        + " AND op.orderNo = :orderNo")
    List<Lesson> findAllByOrderProgressOrderNo(@Param("orderNo") String orderNo);

    @Query("SELECT l.titleCode FROM Lesson l ORDER BY l.titleCode DESC")
    List<String> findLatestTitleCode();


    @Query("SELECT l FROM Lesson l WHERE l.trainer.no = ?1")
    Optional<Lesson> findByTrainerNo(Long trainerNo);
}
