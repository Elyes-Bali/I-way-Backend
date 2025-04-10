package com.example.project.backend.repositories;

import com.example.project.backend.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findBySenderAndReceiver(String sender, String receiver);

    List<Chat> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    List<Chat> findBySenderIdAndReceiverIdOrSenderIdAndReceiverId(Long senderId1, Long receiverId1, Long senderId2, Long receiverId2);
}
