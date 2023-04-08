package com.example.backend.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id){
        super("Could not find the user with id");

    }
//    public UserNotFoundException(String username){
//
//        super("Could not find the user with username");
//    }
}
