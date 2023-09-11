package com.khteam2.connectgym.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUserId(String userId);

    Member findByUserName(String userName);

    Member findByUserEmail(String userEmail);
}
