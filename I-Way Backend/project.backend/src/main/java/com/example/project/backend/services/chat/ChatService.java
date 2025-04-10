package com.example.project.backend.services.chat;

import com.example.project.backend.entity.Chat;
import com.example.project.backend.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    public Chat saveChatMessage(Long senderId, String sender, Long receiverId, String receiver, String message) {
        Chat chat = new Chat();
        chat.setSenderId(senderId);
        chat.setReceiverId(receiverId);
        chat.setSender(sender);
        chat.setReceiver(receiver);
        chat.setMessage(message);
        chat.setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return chatRepository.save(chat);
    }

    public List<Chat> getChatHistory(Long senderId, Long receiverId) {
        return chatRepository.findBySenderIdAndReceiverIdOrSenderIdAndReceiverId(receiverId, senderId, senderId, receiverId);
    }

    public void deleteChatMessage(Long messageId) {
        chatRepository.deleteById(messageId);
    }


}
