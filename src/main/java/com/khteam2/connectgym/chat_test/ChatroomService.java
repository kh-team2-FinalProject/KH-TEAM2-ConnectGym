package com.khteam2.connectgym.chat_test;

import com.khteam2.connectgym.chat_test.dto.ChatroomDTO;
import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.member.MemberRepository;
import com.khteam2.connectgym.trainer.Trainer;
import com.khteam2.connectgym.trainer.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatroomService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatroomRepository chatroomRepository;
    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;

    public Chatroom enterChatRoom(Long memberNo, Long trainerNo) {
        Member member = memberRepository.findById(memberNo).orElse(null);
        Trainer trainer = trainerRepository.findById(trainerNo).orElse(null);

        if (chatroomRepository.findByMemberNoAndTrainerNo(memberNo, trainerNo) != null) {

            Chatroom chatroom = chatroomRepository.findByMemberNoAndTrainerNo(memberNo, trainerNo);
            List<ChatMessage> chatMessageList = chatMessageRepository.findAllByChatroomNo(chatroom.getNo());

            return chatroom;
        } else {
            Chatroom chatRoom = Chatroom.builder()
                .member(member)
                .trainer(trainer)
                .build();
            return chatroomRepository.save(chatRoom);
        }
    }

    public List<ChatMessage> loadMessage(Chatroom chatroom) {

        return chatMessageRepository.findAllByChatroomNo(chatroom.getNo());
    }

    public Chatroom inChatroom(Long chatroomNo) {

        return chatroomRepository.findById(chatroomNo).orElse(null);
    }

    public List<ChatroomDTO> searchMyTrainerChatroomList(Long mamberNo) {
        List<ChatroomDTO> myChatroomList = new ArrayList<>();
        for (Chatroom chatroom : chatroomRepository.findAllByMemberNo(mamberNo)) {
            ChatroomDTO chatroomDTO = new ChatroomDTO().fromEntity(chatroom);
            myChatroomList.add(chatroomDTO);
        }
        return myChatroomList;
    }

    public List<ChatroomDTO> searchMyMemberChatroomList(Long trainerNO) {
        List<ChatroomDTO> myChatroomList = new ArrayList<>();
        for (Chatroom chatroom : chatroomRepository.findAllByTrainerNo(trainerNO)) {
            ChatroomDTO chatroomDTO = new ChatroomDTO().fromEntity(chatroom);
            myChatroomList.add(chatroomDTO);
        }
        return myChatroomList;
    }

}




