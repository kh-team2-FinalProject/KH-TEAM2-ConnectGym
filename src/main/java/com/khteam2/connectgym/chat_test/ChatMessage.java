package com.khteam2.connectgym.chat_test;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class ChatMessage {

    @Id
    @GeneratedValue
    private Long no;


    private Long chattingRoomNo;

    private String content;
    private String sender;
    private LocalDateTime timestamp;

    public ChatMessage(Long no, Long chattingRoomNo, String content, String sender, LocalDateTime timestamp) {
        this.no = no;
        this.chattingRoomNo = chattingRoomNo;
        this.content = content;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public ChatMessage() {
    }

    //메시지 테스트용 생성자
    public ChatMessage(String content, String sender) {
        this.content = content;
        this.sender = sender;
    }


}
