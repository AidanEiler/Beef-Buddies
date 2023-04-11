/**

 * This class represents the authentication manager for the backend application. It handles user authentication requests
 * by checking if the username and password provided match with the ones stored in the database. If a match is found, it returns
 * the user information, otherwise it returns an error message.
 */
package com.example.backend.controller;
import com.example.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.backend.repository.UserRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@CrossOrigin("http://localhost:3000")
@RestController
public class AuthenticationManager {

    private static AuthenticationManager instance;
    private AuthenticationManager (){}
    public AuthenticationManager getInstance(){
        if(instance == null){
            instance = new AuthenticationManager();
        }
        return instance;
    }
    @Autowired
    private UserRepository userRepository;

    /**
     * This method handles user authentication requests. It checks if the username and password provided match with the ones stored in the database.
     * If a match is found, it returns the user information, otherwise it returns an error message.
     *
     * @param requestUser User object containing the username and password to be authenticated
     * @return ResponseEntity containing either the user information or an error message
     */
    @PostMapping("/user/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody User requestUser) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(requestUser.getUsername()));

        if (user.isPresent() && user.get().getPassword().equals(requestUser.getPassword())) {
            return ResponseEntity.ok().body(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

}
