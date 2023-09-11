package com.khteam2.connectgym.chat_test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne
    @JoinColumn(name = "chatroom_no")
    private Chatroom chatroom;

    private String content;

    private String sender;

    @CreationTimestamp
    private LocalDateTime sendAt;

    //메시지 테스트용 생성자
    public ChatMessage(String content, String sender) {
        this.content = content;
        this.sender = sender;
    }
}
