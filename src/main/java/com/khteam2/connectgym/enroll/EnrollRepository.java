package com.khteam2.connectgym.enroll;

import com.khteam2.connectgym.lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollRepository extends JpaRepository<EnrollDetail, Long> {

    @Query(value="select * from enroll_detail where user_no=?1",nativeQuery = true)
    List<EnrollDetail> memLessonList(Long memberId);

}
