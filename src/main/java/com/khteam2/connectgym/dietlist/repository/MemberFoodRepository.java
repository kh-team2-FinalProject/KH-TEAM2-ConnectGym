package com.khteam2.connectgym.dietlist.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.khteam2.connectgym.dietlist.model.MemberFood;
import com.khteam2.connectgym.member.Member;

public interface MemberFoodRepository extends JpaRepository<MemberFood, Long> {
    List<MemberFood> findByMemberAndRegDate(@Param("member") Member member, @Param("regDate") LocalDate regDate);
}
