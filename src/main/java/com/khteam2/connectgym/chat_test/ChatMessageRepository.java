package com.khteam2.connectgym.chat_test;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    //채팅방 입장시 기존 채팅내역 불러오기 위한 쿼리
    List<ChatMessage> findAllByChatroomNo(Long chatroomNo);
}
