package com.khteam2.connectgym.chat_test;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
    //유저 로그인시 대화중인 모든 채팅방 목록 출력시 사용
    List<Chatroom> findAllByMemberNo(Long memberNo);

    //트레이너 로그인시 대화중인 모든 채팅방 목록 출력시 사용
    List<Chatroom> findAllByTrainerNo(Long trainerNo);

    // 1:1 채팅방을 찾기위한 쿼리
    Chatroom findByMemberNoAndTrainerNo(Long memberNo, Long trainerNo);
}
