/**

 This package contains the Message class, which represents a message sent between users in the application.
 The class defines properties for the message's ID, sender, receiver, content, and timestamp.
 The class is annotated with the Entity annotation to indicate that it is a persistent entity in the database.
 It defines ManyToOne relationships with the sender and receiver users, with the sender_id and receiver_id foreign keys stored in the message table.
 The class also defines constructors for creating new messages, as well as getters and setters for its properties.
 @author jomo
 */
package com.example.backend.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Message {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    // Constructors
    public Message() {
    }

    public Message(User sender, User receiver, String content, Date timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
