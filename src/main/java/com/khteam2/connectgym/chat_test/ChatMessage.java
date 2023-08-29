package com.khteam2.connectgym.chat_test;

import lombok.Data;

@Data
public class ChatMessage {


    private String content;
    private String sender;

    public ChatMessage() {
    }

    public ChatMessage(String content, String sender) {
        this.content = content;
        this.sender = sender;
    }


}
