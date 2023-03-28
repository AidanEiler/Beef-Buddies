package com.example.backend.controller;

import com.example.backend.model.Message;
import com.example.backend.model.User;
import com.example.backend.repository.MessageRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        Long senderId = message.getSender().getId();
        Long receiverId = message.getReceiver().getId();

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new UserNotFoundException(senderId));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new UserNotFoundException(receiverId));

        message.setSender(sender);
        message.setReceiver(receiver);
        message.setTimestamp(new Date());

        return messageRepository.save(message);
    }

    @GetMapping("/{senderId}/{receiverId}")
    public List<Message> getMessages(@PathVariable Long senderId, @PathVariable Long receiverId) {
        userRepository.findById(senderId).orElseThrow(() -> new UserNotFoundException(senderId));
        userRepository.findById(receiverId).orElseThrow(() -> new UserNotFoundException(receiverId));

        return messageRepository.findMessagesBetweenUsers(senderId, receiverId);
    }

    // Add any other endpoints you need, e.g., listConversations
}
