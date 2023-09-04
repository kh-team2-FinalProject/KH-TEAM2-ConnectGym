package com.khteam2.connectgym.chat_test;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {


    //ChatRoom findByMemberNoAndTrainerNo(Long memberNo, Long trainerNo);


    List<Chatroom> findAllByMemberNo(Long memberNo);


//    boolean findByTrainerNo(Long trainerNo);

    //    @Query("select cr from Chatroom cr where cr.member.no=?1 and cr.trainer.no=?2")
    Chatroom findByMemberNoAndTrainerNo(Long memberNo, Long trainerNo);


}
