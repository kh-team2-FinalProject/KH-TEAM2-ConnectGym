package com.khteam2.connectgym.chat_test;

import com.khteam2.connectgym.chat_test.dto.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatroomRepository chatroomRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public ChatMessage saveMessage(Long chatroomNo, ChatMessageDTO chatMessageDTO) {
        chatMessageDTO.setChatroom(chatroomRepository.findById(chatroomNo).orElse(null));
        ChatMessage chatMessage = chatMessageDTO.toEntity();

        return chatMessageRepository.save(chatMessage);
    }
}
