
// com/example/backend/controller/UserController.java
package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.exception.UserNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    @GetMapping("/users")
    List<User> getAllUsers() {
        return userRepository.findAll();

    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setFirst_name(newUser.getFirst_name());
                    user.setLast_name(newUser.getLast_name());
                    user.setEmail(newUser.getEmail());
                    user.setPassword(newUser.getPassword());
                    return userRepository.save(user);
                }).orElseThrow(() -> new UserNotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);

        return "User with id "+id+" has been deleted";
    }
    @PostMapping("/user/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody User requestUser) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(requestUser.getUsername()));

        if (user.isPresent() && user.get().getPassword().equals(requestUser.getPassword())) {
            return ResponseEntity.ok().body(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
    @GetMapping("/users/basic")
    public List<Map<String, Object>> getAllUsersBasicInfo(@RequestParam("exclude") Long excludeUserId) {
        return userRepository.findAllBasicInfoExcept(excludeUserId);
    }


    @GetMapping("/user/{id}/friends")
    public Set<User> getUserFriends(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return user.getFriends();
    }



    @PostMapping("/user/{userId}/addFriend/{friendId}")
    public ResponseEntity<?> addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new UserNotFoundException(friendId));

        user.addFriend(friend);

        userRepository.save(user);

        return ResponseEntity.ok().body("Friend added successfully");
    }










}
