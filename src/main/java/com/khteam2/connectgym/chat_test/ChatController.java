package com.khteam2.connectgym.chat_test;

import com.khteam2.connectgym.chat_test.dto.ChatMessageDTO;
import com.khteam2.connectgym.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ChatController {


    private final MemberService memberService;
    private final ChatMessageService chatMessageService;
    private final ChatroomService chatroomService;

    @GetMapping("/chat_test/{chatroomNO}")
    public String chattest() {


        return "/chat_test/chat_test2";
    }

    @PostMapping("/checkedChatroom")
    @ResponseBody
    public Long checkedChatroom(@RequestParam Long memberNo, @RequestParam Long trainerNo) {

        System.out.println(memberNo + "왔다 트레이너" + trainerNo);

        Long chatroomNo = chatroomService.enterChatRoom(memberNo, trainerNo).getNo();

        return chatroomNo;


    }

    @MessageMapping("/chat") // 클라이언트가 메시지 보낼 때의 엔드포인트 설정
    @SendTo("/topic/qqq") // 브로커 주소 설정
    public ChatMessage sendMessage(ChatMessageDTO message) {
        System.out.println(message);

        // 메시지 처리 로직 구현
        return chatMessageService.saveMessage(message);
    }
}
