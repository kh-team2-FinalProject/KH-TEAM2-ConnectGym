package com.khteam2.connectgym.chat_test;

import com.khteam2.connectgym.chat_test.dto.ChatMessageDTO;
import com.khteam2.connectgym.chat_test.dto.ChatMessageReaponseDTO;
import com.khteam2.connectgym.common.SessionConstant;
import com.khteam2.connectgym.member.MemberClass;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatController {
    private final ChatMessageService chatMessageService;
    private final ChatroomService chatroomService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/chatting/{chatroomNo}")
    public String chat_open(Model model,
                            @PathVariable("chatroomNo") Long chatroomNo,
                            @SessionAttribute(name = SessionConstant.LOGIN_MEMBER_CLASS, required = false) MemberClass memberClass) {
        //chatroomNo로 특정된 채팅룸 입장
        Chatroom chatroom = chatroomService.inChatroom(chatroomNo);
        //지난 대화내용 전송
        List<ChatMessageReaponseDTO> chatMessages = chatroomService.loadMessage(chatroom);
        model.addAttribute("chatMessages", chatMessages);
        model.addAttribute("chatroomNo", chatroomNo);
        model.addAttribute("chatroom", chatroom);

        //sender  interlocutor 구분해서 모델전송
        String sender = "";
        String interlocutor = "";
        if (memberClass == MemberClass.MEMBER) {
            sender = chatroom.getMember().getUserName();
            interlocutor = chatroom.getTrainer().getTrainerName();
        } else {
            sender = chatroom.getTrainer().getTrainerName();
            interlocutor = chatroom.getMember().getUserName();
        }
        model.addAttribute("sender", sender);
        model.addAttribute("interlocutor", interlocutor);
        return "chattingroom/chatting";
    }

    @PostMapping("/checkedChatroom")
    @ResponseBody
    public Chatroom checkedChatroom(@RequestParam Long memberNo, @RequestParam Long trainerNo, Model model) {
        Chatroom chatroom = chatroomService.enterChatRoom(memberNo, trainerNo);

        return chatroom;
    }

    @MessageMapping("/chat/{chatroomNo}") // 클라이언트가 메시지 보낼 때의 엔드포인트 설정
    public void sendMessage(@DestinationVariable Long chatroomNo,
                            @Payload ChatMessageDTO message) {
        //채팅 메시지 DB저장
        ChatMessage chatMessage = chatMessageService.saveMessage(chatroomNo, message);
        //리턴된 엔티티의 DTO화
        ChatMessageReaponseDTO chatMessageReaponseDTO = new ChatMessageReaponseDTO().fromEntity(chatMessage);
        log.error(chatMessageReaponseDTO.getSendAt());
        // 채팅메시지 수신하는 주소
        String claQueue = "/queue/qqq/" + chatroomNo;
        // 전송
        simpMessagingTemplate.convertAndSend(claQueue, chatMessageReaponseDTO);
    }
}
