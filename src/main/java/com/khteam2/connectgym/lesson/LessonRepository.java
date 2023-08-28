package com.khteam2.connectgym.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Query(value = "SELECT l.* FROM users u" + " JOIN orders o ON o.member_no = u.`no`"
        + " JOIN order_detail od ON od.order_no = o.`no`"
        + " JOIN lessons l ON l.`no` = od.lesson_no"
        + " WHERE u.`no` = :memberId", nativeQuery = true)
    List<Lesson> findByMemberNo(@Param("memberId") Long memberNo);

}
