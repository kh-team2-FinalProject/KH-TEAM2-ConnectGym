package com.khteam2.connectgym.chat_test;

import com.khteam2.connectgym.chat_test.dto.ChatMessageDTO;
import com.khteam2.connectgym.chat_test.dto.ChatMessageReaponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final ChatroomService chatroomService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/chat_test/{chatroomNo}")
    public String chat_open(Model model,
                            @PathVariable("chatroomNo") Long chatroomNo) {
        Chatroom chatroom = chatroomService.inChatroom(chatroomNo);
//        ChatroomDTO chatroomDTO = new ChatroomDTO().fromEntity(chatroom);
        System.out.println("chatroomNo=====================");
        System.out.println(chatroomNo);
//        System.out.println(chatroomDTO);
        model.addAttribute("chatroomNo", chatroomNo);
//        model.addAttribute("chatroomDTO", chatroomDTO);
        model.addAttribute("chatroom", chatroom);


        return "chat_test/chat_test2";
    }
//    @PostMapping("/chat_test/{chatroomNo}")
//    public String chat_open(@PathVariable("chatroomNO") Long chatroomNo, Model model) {
//
//
//        String sender = chatroom.getMember().getUserName();
//        model.addAttribute("sender", sender);
//
//        return "/chat_test/chat_test2";
//    }

    @PostMapping("/checkedChatroom")
    @ResponseBody
    public Chatroom checkedChatroom(@RequestParam Long memberNo, @RequestParam Long trainerNo) {
        return chatroomService.enterChatRoom(memberNo, trainerNo);
    }

    @MessageMapping("/chat/{chatroomNo}") // 클라이언트가 메시지 보낼 때의 엔드포인트 설정
    public void sendMessage(@DestinationVariable Long chatroomNo,
                            @Payload ChatMessageDTO message) {

        System.out.println("chatroomNo = " + chatroomNo);
        System.out.println("message = " + message);
        ChatMessage chatMessage = chatMessageService.saveMessage(chatroomNo, message);
        ChatMessageReaponseDTO chatMessageReaponseDTO = new ChatMessageReaponseDTO().fromEntity(chatMessage);

        System.out.println("chatMessageDTO = " + chatMessageReaponseDTO);
        String claQueue = "/queue/qqq/" + chatroomNo;


        simpMessagingTemplate.convertAndSend(claQueue, chatMessageReaponseDTO);

    }
}
