package com.example.demo;

import org.springframework.stereotype.Service;

/*
 * Handles sending and receiving texts between users
 */
@Service
public class MessagingManager {
	
	/*
	 * Sends a text to the other user
	 * @param user1 Text sender
	 * @param user2 Text receiver
	 */
	public void sendText(User user1, User user2) {
		//updates database with text
	}
	
	/*
	 * Gets texts from the database
	 * @param user1 The receiver of the text
	 * @param user2 The sender of the text
	 */
	public void getText(User user1, User user2) {
		//gets texts from database
	}
}
