package com.khteam2.connectgym.chat_test.dto;

import com.khteam2.connectgym.chat_test.Chatroom;
import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.trainer.Trainer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatroomDTO {
    private Long no;
    private Member member;
    private Trainer trainer;

    public Chatroom toEntity() {
        return Chatroom.builder()
            .member(member)
            .trainer(trainer)
            .build();
    }

    public ChatroomDTO fromEntity(Chatroom chatroom) {
        return ChatroomDTO.builder()
            .no(chatroom.getNo())
            .trainer(chatroom.getTrainer())
            .member(chatroom.getMember())
            .build();
    }
}
