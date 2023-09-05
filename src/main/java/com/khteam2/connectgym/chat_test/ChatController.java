package com.khteam2.connectgym.chat_test;

import com.khteam2.connectgym.chat_test.dto.ChatMessageDTO;
import com.khteam2.connectgym.chat_test.dto.ChatMessageReaponseDTO;
import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.member.MemberClass;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final ChatroomService chatroomService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/chat_test/{chatroomNo}")
    public String chat_open(Model model,
                            @PathVariable("chatroomNo") Long chatroomNo,
                            @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_CLASS, required = false) MemberClass memberClass) {
        Chatroom chatroom = chatroomService.inChatroom(chatroomNo);
//        ChatroomDTO chatroomDTO = new ChatroomDTO().fromEntity(chatroom);
        System.out.println("chatroomNo=====================");
        System.out.println(chatroomNo);
//        System.out.println(chatroomDTO);
        List<ChatMessage> chatMessages = chatroomService.loadMessage(chatroom);
        model.addAttribute("chatMessages", chatMessages);
        model.addAttribute("chatroomNo", chatroomNo);
//        model.addAttribute("chatroomDTO", chatroomDTO);
        model.addAttribute("chatroom", chatroom);
        String sender = "";

        if (memberClass == MemberClass.MEMBER) {
            sender = chatroom.getMember().getUserName();
        } else {
            sender = chatroom.getTrainer().getTrainerName();
        }
        model.addAttribute("sender", sender);

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
    public Chatroom checkedChatroom(@RequestParam Long memberNo, @RequestParam Long trainerNo, Model model) {

        Chatroom chatroom = chatroomService.enterChatRoom(memberNo, trainerNo);

        return chatroom;
    }

    @MessageMapping("/chat/{chatroomNo}") // 클라이언트가 메시지 보낼 때의 엔드포인트 설정
    public void sendMessage(@DestinationVariable Long chatroomNo,
                            @Payload ChatMessageDTO message) {


        ChatMessage chatMessage = chatMessageService.saveMessage(chatroomNo, message);
        ChatMessageReaponseDTO chatMessageReaponseDTO = new ChatMessageReaponseDTO().fromEntity(chatMessage);

        System.out.println("chatMessageDTO = " + chatMessageReaponseDTO);
        String claQueue = "/queue/qqq/" + chatroomNo;


        simpMessagingTemplate.convertAndSend(claQueue, chatMessageReaponseDTO);

    }
}
