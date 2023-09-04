package com.khteam2.connectgym.chat_test.dto;

import com.khteam2.connectgym.chat_test.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageReaponseDTO {
    private Long chatroomNo;
    private String content;
    private String sender;
//    private LocalDateTime sendAt;


    public ChatMessageReaponseDTO fromEntity(ChatMessage chatMessage) {
        return ChatMessageReaponseDTO.builder()
            .chatroomNo(chatMessage.getChatroom().getNo())
            .content(chatMessage.getContent())
            .sender(chatMessage.getSender())
//            .sendAt(chatMessage.getSendAt())
            .build();
    }
}
