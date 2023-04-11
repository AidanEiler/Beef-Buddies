
package com.example.backend.exception;

public class UserNotFoundException extends RuntimeException{
    /**
     * Constructor for the UserNotFoundException class that takes a user ID.
     *
     * @param id the ID of the user that was not found
     */
    public UserNotFoundException(Long id) {
        super("Could not find the user with ID: " + id);
    }

    /**
     * Constructor for the UserNotFoundException class that takes a username.
     *
     * @param username the username of the user that was not found
     */
    public UserNotFoundException(String username) {
        super("Could not find the user with username: " + username);
    }

}
