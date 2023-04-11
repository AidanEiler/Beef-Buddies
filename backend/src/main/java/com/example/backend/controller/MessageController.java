
/**

* The MessageController class manages the http endpoints for sending and receiving messages between users.
 */
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

    private static MessageController instance;
    private MessageController (){}
    public MessageController getInstance(){
        if(instance == null){
            instance = new MessageController();
        }
        return instance;
    }
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    /**
     * This method sends a new message from a sender to a receiver.
     * @param message The Message object containing the sender, receiver, content, and timestamp.
     * @return The Message object that was saved to the database.
     * @throws UserNotFoundException If the sender or receiver is not found in the database.
     */
    @PostMapping
    public Message sendMessage(@RequestBody Message message) throws UserNotFoundException {
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

    /**
     * This method retrieves all messages between a sender and receiver.
     * @param senderId The ID of the sender User object.
     * @param receiverId The ID of the receiver User object.
     * @return A List of Message objects exchanged between the sender and receiver.
     * @throws UserNotFoundException If the sender or receiver is not found in the database.
     */
    @GetMapping("/{senderId}/{receiverId}")
    public List<Message> getMessages(@PathVariable Long senderId, @PathVariable Long receiverId) throws UserNotFoundException {
        userRepository.findById(senderId).orElseThrow(() -> new UserNotFoundException(senderId));
        userRepository.findById(receiverId).orElseThrow(() -> new UserNotFoundException(receiverId));

        return messageRepository.findMessagesBetweenUsers(senderId, receiverId);
    }

}
