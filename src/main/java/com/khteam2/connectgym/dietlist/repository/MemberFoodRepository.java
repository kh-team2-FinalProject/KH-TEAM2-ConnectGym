package com.khteam2.connectgym.dietlist.repository;

import com.khteam2.connectgym.dietlist.model.MemberFood;
import com.khteam2.connectgym.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MemberFoodRepository extends JpaRepository<MemberFood, Long> {
    List<MemberFood> findByMemberAndRegDate(@Param("member") Member member, @Param("regDate") LocalDate regDate);

    MemberFood findByMemberAndNo(Member member, Long no);

    List<MemberFood> findByRegDateBetween(LocalDate startDate, LocalDate endDate);

    List<MemberFood> findByMemberNoAndRegDateBetween(Long memverNo, LocalDate startDate, LocalDate endDate);
}
