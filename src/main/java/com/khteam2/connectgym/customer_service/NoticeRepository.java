package com.khteam2.connectgym.customer_service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByOrderByNoticeDatetimeDesc();
}
