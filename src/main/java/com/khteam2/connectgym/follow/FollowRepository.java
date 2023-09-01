package com.khteam2.connectgym.follow;

import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.trainer.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findByFromUser(Member fromUser);

    @Modifying
    @Query("delete from Follow f where f.fromUser=?1 and f.toTrainer=?2")
    void deleteByFromUserAndToTrainer(Member fromUser, Trainer toTrainer);

    //트레이너를 위한 자신의 팔로우 회원 목록 출력
    List<Follow> findAllByToTrainer(Long toTrainerNo);

    //유저가 팔로우한 트레이너 목록 출력
    List<Follow> findAllByFromUser(Long fromUserNoo);

    //사용자를 위한 트레이너 팔로우된 수 출력
    @Query("select count(f) from Follow f where f.toTrainer.no=?1")
    int findAllByToTrainerCount(Long toTrainerNo);

    @Query("select count(f) from Follow f where f.fromUser.no=?1 and f.toTrainer.no=?2")
    int findAllByFromUserNoAndTrainerNo(Long userNo, Long trainerNo);
}
