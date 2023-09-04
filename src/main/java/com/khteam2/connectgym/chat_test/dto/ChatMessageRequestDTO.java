//package com.khteam2.connectgym.chat_test.dto;
//
//import com.khteam2.connectgym.chat_test.ChatMessage;
//import com.khteam2.connectgym.chat_test.Chatroom;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//
//@Data
//@RequiredArgsConstructor
//
//public class ChatMessageRequestDTO {
//
//    private Long no;
//    private Chatroom chatroom;
//    private String content;
//    private String sender;
////    private LocalDateTime sendAt;
//
//
//    public ChatMessage toEntity() {
//        return ChatMessage.builder()
//            .chatroom(chatroom)
//            .content(content)
//            .sender(sender)
////                .sendAt(sendAt)
//            .build();
//    }
//
//}
