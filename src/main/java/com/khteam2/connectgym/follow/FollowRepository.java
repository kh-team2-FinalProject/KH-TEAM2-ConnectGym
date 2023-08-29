package com.khteam2.connectgym.follow;

import com.khteam2.connectgym.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Member> {


    List<Follow> findByFromUser(Member fromUser);
}
