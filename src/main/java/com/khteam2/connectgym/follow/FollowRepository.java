package com.khteam2.connectgym.follow;

import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.trainer.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    @Modifying
    @Query("DELETE FROM Follow f WHERE f.fromUser = ?1 AND f.toTrainer = ?2")
    void deleteByFromUserAndToTrainer(Member fromUser, Trainer toTrainer);

    //트레이너를 위한 자신의 팔로우 회원 목록 출력
    @Query("SELECT f FROM Follow f WHERE f.toTrainer.no = ?1")
    List<Follow> findAllByToTrainer(Long toTrainerNo);

    //트레이너 팔로우된 수 출력
    @Query("SELECT COUNT(f) FROM Follow f WHERE f.toTrainer.no = ?1")
    Optional<Integer> findAllByToTrainerCount(Long toTrainerNo);

    //유저가 팔로우한 트레이너 목록 출력
    @Query("SELECT f FROM Follow f WHERE f.fromUser.no = ?1")
    List<Follow> findAllByFromUser(Long fromUserNo);

    // 로그인 사용자 팔로우 여부 체크
    @Query("SELECT COUNT(f) FROM Follow f WHERE f.fromUser.no = ?1 AND f.toTrainer.no = ?2")
    int findAllByFromUserNoAndTrainerNo(Long userNo, Long trainerNo);

    // 특정 사용자가 특정 트레이너의 이름을 포함하는 팔로우 검색
    @Query(value = "SELECT f FROM Follow f "
        + "JOIN f.toTrainer t "
        + "WHERE f.fromUser.no = ?1 "
        + "AND t.trainerName LIKE CONCAT('%', TRIM(?2), '%')")
    List<Follow> searchByTrainerName(Long fromUserNo, String search);
}
