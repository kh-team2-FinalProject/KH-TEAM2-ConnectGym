package com.khteam2.connectgym.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUserId(String userId);

    Member findByUserName(String userName);

    Optional<Member> findByProviderAndProviderId(String provider, String providerId);
}
