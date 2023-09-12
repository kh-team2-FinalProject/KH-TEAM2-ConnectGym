package com.khteam2.connectgym.like;

import com.khteam2.connectgym.lesson.Lesson;
import com.khteam2.connectgym.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    // 사용자와 레슨 사이의 좋아요 관계 삭제
    @Modifying
    @Query("DELETE FROM Like l WHERE l.member = ?1 AND l.lesson = ?2")
    void deleteByMemberAndLesson(Member member, Lesson lesson);

    //레슨별 위한 찜한 회원 목록 출력 => 필요없을 것 같음
    @Query("SELECT l FROM Like l WHERE l.lesson.no = ?1")
    List<Like> findAllByLessonNo(Long lessonNo);

    //레슨 찜 수 출력
    @Query("SELECT COUNT(l) FROM Like l WHERE l.lesson.no = ?1")
    Optional<Integer> findAllByLessonCount(Long lessonNo);

    //유저가 찜한 레슨 목록 출력
    @Query("SELECT l FROM Like l WHERE l.member.no = ?1")
    List<Like> findAllByMember(Long member);

    // 로그인 사용자 찜 여부 체크
    @Query("SELECT COUNT(l) FROM Like l WHERE l.member.no = ?1 AND l.lesson.no = ?2")
    int findAllByMemberNoAndLessonNo(Long userNo, Long lessonNo);

    // 특정 사용자가 특정 트레이너의 이름 또는 레슨 제목을 포함하는 좋아요 검색
    @Query(value = "SELECT l FROM Like l"
        + " JOIN l.lesson le"
        + " JOIN l.lesson.trainer t"
        + " JOIN l.member m"
        + " WHERE m.no = ?1"
        + " AND (t.trainerName LIKE CONCAT('%', TRIM(?2), '%')"
        + " OR le.title LIKE CONCAT('%', TRIM(?2), '%'))")
    List<Like> searchByTrainerNameOrLessonTitle(Long userNo, String search);
}
