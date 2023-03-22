package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/*
 * Authenticates login and sign up attempts
 */
@Service
public class AuthenticationManager {
	
	@Autowired
    private UserService userService;
	
	/*
	 * Allows users to make a new account and checks if the account already exists
	 * @param user User object containing the info of the new user
	 * @return The confirmation or denial of the user creation
	 */
	public ResponseEntity<?> signup(@RequestBody User user) {
        if (userService.existsByEmailOrUsername(user.getEmail(), user.getName())) {
            return ResponseEntity.badRequest().body("Email or username already exists");
        }
        User savedUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
	
	/*
	 * Allows pre-existing users to sign in and checks if the login is valid
	 * @param user User object containing the info submitted for authentication
	 * @return The confirmation or denial of the login attempt
	 */
	public ResponseEntity<?> login(@RequestBody User user) {
        User foundUser = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (foundUser == null) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }
        return ResponseEntity.ok(foundUser);
    }
	
	/*
	 * Retrieves user from the database using their id
	 * @param id Path variable to the user
	 * @return The user for a not found
	 */
	public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}
