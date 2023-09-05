package com.khteam2.connectgym.chat_test;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByChatroomNo(Long chatroonNo);

}
