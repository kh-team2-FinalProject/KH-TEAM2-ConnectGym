package com.khteam2.connectgym.chat_test;

import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.member.MemberRepository;
import com.khteam2.connectgym.trainer.Trainer;
import com.khteam2.connectgym.trainer.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatroomService {


    private final ChatroomRepository chatroomRepository;
    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;

    public Chatroom enterChatRoom(Long memberNo, Long trainerNo) {
        Member member = memberRepository.findById(memberNo).orElse(null);
        Trainer trainer = trainerRepository.findById(trainerNo).orElse(null);
//        if (chatRoomRepository.findByMemberNo(memberNo) == chatRoomRepository.findByTrainerNo(trainerNo))


        if (chatroomRepository.findByMemberNoAndTrainerNo(memberNo, trainerNo) != null) {
            return chatroomRepository.findByMemberNoAndTrainerNo(memberNo, trainerNo);
        } else {
            Chatroom chatRoom = Chatroom.builder()
                .member(member)
                .trainer(trainer)
                .build();


            return chatroomRepository.save(chatRoom);
        }


    }

}




