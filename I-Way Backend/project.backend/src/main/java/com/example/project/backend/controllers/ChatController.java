package com.example.project.backend.controllers;

import com.example.project.backend.entity.Chat;
import com.example.project.backend.services.chat.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "http://localhost:4200")
public class ChatController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
//    @MessageMapping("/chat/{senderId}/{receiverId}")
//    @SendTo("/topic/chat/{senderId}-{receiverId}")
//    public String sendMessage(@DestinationVariable Long senderId, @DestinationVariable Long receiverId, String message) {
//        // Parse the message from JSON to get the full structure
//        // Ensure the message is a JSON string with sender, receiver, and content
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, String> messageMap = objectMapper.readValue(message, Map.class);
//            String sender = messageMap.get("senderName");  // Get sender's name
//            String receiver = messageMap.get("receiverName");  // Get receiver's name
//            String content = messageMap.get("content");  // Get message content
//
//            // Save the message to the database with user IDs
//            Chat savedChat = chatService.saveChatMessage(senderId, sender, receiverId, receiver, content);
//
//            // Create a response object containing the necessary information
//            Map<String, String> response = Map.of(
//                    "id", savedChat.getId().toString(),
//                    "senderName", sender,
//                    "receiverName", receiver,
//                    "senderId", senderId.toString(),
//                    "receiverId", receiverId.toString(),
//                    "message", content
//            );
//
//            // Return the response as a JSON string
//            return objectMapper.writeValueAsString(response);
//
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            return "Error processing message";
//        }
//    }

    @MessageMapping("/chat/{senderId}/{receiverId}")
    @SendTo("/topic/chat/{senderId}-{receiverId}")
    public String sendMessage(@DestinationVariable Long senderId, @DestinationVariable Long receiverId, String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> messageMap = objectMapper.readValue(message, Map.class);
            String sender = messageMap.get("senderName");
            String receiver = messageMap.get("receiverName");
            String content = messageMap.get("content");

            // Save the message to the database
            Chat savedChat = chatService.saveChatMessage(senderId, sender, receiverId, receiver, content);

            // Ensure senderId and receiverId are correctly included in the response
            Map<String, Object> response = Map.of(
                    "id", savedChat.getId(),
                    "senderName", sender,
                    "receiverName", receiver,
                    "senderId", senderId,
                    "receiverId", receiverId,
                    "message", content
            );

            return objectMapper.writeValueAsString(response);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Error processing message";
        }
    }



    @PostMapping("/history")
    public List<Chat> getChatHistory(@RequestBody String senderReceiver) {
        String[] parts = senderReceiver.split(":");
        Long senderId = Long.parseLong(parts[0]);
        Long receiverId = Long.parseLong(parts[1]);
        return chatService.getChatHistory(senderId, receiverId);
    }


    @DeleteMapping("/message/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long messageId) {
        try {
            chatService.deleteChatMessage(messageId);
            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Message deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete message");
        }
    }

//    @MessageMapping("/delete/{senderId}/{receiverId}/{messageId}")
//    @SendTo("/topic/chat/{senderId}-{receiverId}")
//    public String deleteMessage(@DestinationVariable Long senderId,
//                                @DestinationVariable Long receiverId,
//                                @DestinationVariable Long messageId) {
//        try {
//            chatService.deleteChatMessage(messageId);
//
//            // Create a deletion notification message
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, Object> response = Map.of(
//                    "id", messageId,
//                    "type", "delete"
//            );
//
//            return objectMapper.writeValueAsString(response); // Notify users
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error deleting message";
//        }
//    }

    @MessageMapping("/chat/delete/{senderId}/{receiverId}/{messageId}")
    @SendTo("/topic/chat/{senderId}-{receiverId}")
    public Map<String, Object> deleteMessageBroadcast(@DestinationVariable Long senderId,
                                                      @DestinationVariable Long receiverId,
                                                      @DestinationVariable Long messageId) {
        try {
            chatService.deleteChatMessage(messageId);

            return Map.of(
                    "id", messageId,
                    "type", "delete"
            );
        } catch (Exception e) {
            return Map.of(
                    "id", -1L,
                    "type", "error"
            );
        }
    }
//    @MessageMapping("/videoCall/offer")
//    @SendTo("/topic/videoCall/{receiverId}")
//    public void handleOffer(@DestinationVariable Long receiverId, String offer) {
//        // Send the offer to the receiver
//        messagingTemplate.convertAndSend("/topic/videoCall/" + receiverId, offer);
//    }
//
//    @MessageMapping("/videoCall/answer")
//    @SendTo("/topic/videoCall/{receiverId}")
//    public void handleAnswer(@DestinationVariable Long receiverId, String answer) {
//        // Send the answer to the receiver
//        messagingTemplate.convertAndSend("/topic/videoCall/" + receiverId, answer);
//    }
//
//    @MessageMapping("/videoCall/iceCandidate")
//    @SendTo("/topic/videoCall/{receiverId}")
//    public void handleIceCandidate(@DestinationVariable Long receiverId, String candidate) {
//        // Send ICE candidate to the receiver
//        messagingTemplate.convertAndSend("/topic/videoCall/" + receiverId, candidate);
//    }

}
