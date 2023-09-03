package com.khteam2.connectgym.like;


import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {


    @Modifying
    @Query("delete from Like l where l.member=?1 and l.lesson=?2")
    void deleteByMemberAndLesson(Member member, Lesson lesson);

    //레슨별 위한 찜한 회원 목록 출력 => 필요없을 것 같음
    @Query("select l from Like l where l.lesson.no=?1")
    List<Like> findAllByLessonNo(Long lessonNo);

    //레슨 찜 수 출력
    @Query("select count(l) from Like l where l.lesson.no=?1")
    int findAllByLessonCount(Long lessonNo);

    //유저가 찜한 레슨 목록 출력
    @Query("select l from Like l where l.member.no=?1")
    List<Like> findAllByMember(Long member);

    // 로그인 사용자 찜 여부 체크
    @Query("select count(l) from Like l where l.member.no=?1 and l.lesson.no=?2")
    int findAllByMemberNoAndLessonNo(Long userNo, Long lessonNo);
}
