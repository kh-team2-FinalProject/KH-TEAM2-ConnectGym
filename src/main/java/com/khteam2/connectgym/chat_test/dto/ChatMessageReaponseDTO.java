package com.khteam2.connectgym.chat_test.dto;

import com.khteam2.connectgym.chat_test.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageReaponseDTO {
    private Long chatroomNo;
    private String content;
    private String sender;
    private String sendAt;

    public ChatMessageReaponseDTO fromEntity(ChatMessage chatMessage) {
        return ChatMessageReaponseDTO.builder()
            .chatroomNo(chatMessage.getChatroom().getNo())
            .content(chatMessage.getContent())
            .sender(chatMessage.getSender())
            //Localdatetime 을 원하는 포맷으로 변환
            .sendAt(chatMessage.getSendAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
            .build();
    }
}
