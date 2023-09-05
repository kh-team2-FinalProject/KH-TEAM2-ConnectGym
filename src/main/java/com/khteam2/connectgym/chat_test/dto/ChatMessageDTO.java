package com.khteam2.connectgym.chat_test.dto;

import com.khteam2.connectgym.chat_test.ChatMessage;
import com.khteam2.connectgym.chat_test.Chatroom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {

    private Long no;
    private Chatroom chatroom;
    private String content;
    private String sender;
//    private LocalDateTime sendAt;


    public ChatMessage toEntity() {
        return ChatMessage.builder()
            .chatroom(chatroom)
            .content(content)
            .sender(sender)
//                .sendAt(sendAt)
            .build();
    }

    public ChatMessageDTO formEntity(ChatMessage chatMessage) {
        return ChatMessageDTO.builder()
            .no(chatMessage.getNo())
            .chatroom(chatMessage.getChatroom())
            .content(chatMessage.getContent())
            .sender(chatMessage.getSender())
//            .sendAt(chatMessage.getSendAt())
            .build();
    }

}
