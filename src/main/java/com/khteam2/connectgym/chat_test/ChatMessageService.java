package com.khteam2.connectgym.chat_test;


import com.khteam2.connectgym.chat_test.dto.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessage saveMessage(@NotNull ChatMessageDTO chatMessageDTO) {

        ChatMessage chatMessage = chatMessageDTO.toEntity();

        return chatMessageRepository.save(chatMessage);


    }

}
