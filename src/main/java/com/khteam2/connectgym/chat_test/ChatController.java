package com.khteam2.connectgym.chat_test;

import com.khteam2.connectgym.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ChatController {


    private final MemberService memberService;


    @GetMapping("/chat_test")
    public String chattest() {
        return "/chat_test/chat_test";
    }

    @MessageMapping("/chat") // 클라이언트가 메시지 보낼 때의 엔드포인트 설정
    @SendTo("/topic/qqq") // 브로커 주소 설정
    public ChatMessage sendMessage(ChatMessage message) {
        System.out.println(message);

        // 메시지 처리 로직 구현
        return message;
    }
}
