
// com/example/backend/controller/UserController.java
package com.example.backend.controller;

import com.example.backend.MatchingStrategy;
import com.example.backend.model.User;
import com.example.backend.repository.MessageRepository;

import com.example.backend.repository.UserRepository;
import com.example.backend.strategy.ArmsStrategy;
import com.example.backend.strategy.DefaultStrategy;
import com.example.backend.strategy.LegsStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.exception.UserNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;



@RestController
@CrossOrigin("http://localhost:3000")

public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;


    @Autowired
    private DefaultStrategy defaultStrategy;


    @Autowired
    private ArmsStrategy armsStrategy;

    @Autowired
    private LegsStrategy legsStrategy;

    @PostMapping("/user")
    User newUser(@RequestBody User newUser) {

        if (newUser.getBench() == null) {
            newUser.setBench(0L); // Set default value if bench is null
        }
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

    @GetMapping("/user")
    User getUserByUsername(@RequestParam String username) {
        return userRepository.findByUsername(username);
//               d .orElseThrow(() -> new UserNotFoundException(username));
    }



//    @PutMapping("/user/{id}")
//    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
//        return userRepository.findById(id)
//                .map(user -> {
//                    user.setUsername(newUser.getUsername());
//                    user.setFirst_name(newUser.getFirst_name());
//                    user.setLast_name(newUser.getLast_name());
//                    user.setEmail(newUser.getEmail());
//                    user.setPassword(newUser.getPassword());
//                    user.setBench(newUser.getBench());
//                    user.setSquat(newUser.getSquat());
//                    return userRepository.save(user);
//                }).orElseThrow(() -> new UserNotFoundException(id));
//    }

    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id, @RequestParam(required = false, name = "picture") MultipartFile picture) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setFirst_name(newUser.getFirst_name());
                    user.setLast_name(newUser.getLast_name());
                    user.setEmail(newUser.getEmail());
                    user.setPassword(newUser.getPassword());
                    user.setBench(newUser.getBench());
                    user.setSquat(newUser.getSquat());
                    user.setCurl(newUser.getCurl());

                    if (picture != null && !picture.isEmpty()) {
                        try {
                            byte[] pictureBytes = picture.getBytes();
                            user.setProfilePicture(pictureBytes);
                        } catch (IOException e) {
                            // handle exception
                        }
                    }

                    return userRepository.save(user);
                }).orElseThrow(() -> new UserNotFoundException(id));
    }




    // UserController.java
    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        User user = userRepository.getById(id);

        userRepository.deleteById(id);

        return "User with id " + id + " has been deleted";
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
        User excludeUser = userRepository.findById(excludeUserId)
                .orElseThrow(() -> new UserNotFoundException(excludeUserId));

        List<Long> friendIds = excludeUser.getFriends().stream().map(User::getId).collect(Collectors.toList());
        friendIds.add(excludeUserId); // Add the exclude user ID to the list

        return userRepository.findAllBasicInfoExcept(excludeUserId, friendIds);
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



    @GetMapping("/user/{id}/matches")
    public ResponseEntity<List<User>> getMatches(@PathVariable Long id) {
        List<User> matches = defaultStrategy.match(id);
        return ResponseEntity.ok(matches);
    }
    @GetMapping("/user/{id}/armsMatches")
    public ResponseEntity<List<User>>  getArmsMatches(@PathVariable Long id){
        List<User> matches = armsStrategy.match(id);
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/user/{id}/legsMatches")
    public ResponseEntity<List<User>>  getLegMatches(@PathVariable Long id){
        List<User> matches = legsStrategy.match(id);
        return ResponseEntity.ok(matches);
    }


//    @GetMapping("matchmake/default")
//    public List<User> MatchmakeDefault(@PathVariable Long id) {
//        MatchingStrategy def = new DefaultStrategy();
//        matcher = new Matcher(def);
//        return matcher.match(id);
//    }


//    @GetMapping("/user/{id}/matches")
//    public List<User> getMatches(@PathVariable Long id) {
//        User targetUser = userRepository.findById(id)
//                .orElseThrow(() -> new UserNotFoundException(id));
//
//        double targetBench = targetUser.getBench() * 0.4; // Assign weights to each value
//        double targetSquat = targetUser.getSquat() * 0.3;
//        double targetCurl = targetUser.getCurl() * 0.3;
//
//        List<User> allUsers = userRepository.findAll();
//        allUsers.remove(targetUser); // Remove the target user from the list
//
//        // Calculate distance for each user and add to a TreeMap
//        TreeMap<Double, User> distanceMap = new TreeMap<>();
//        for (User user : allUsers) {
//            double userBench = user.getBench() * 0.4;
//            double userSquat = user.getSquat() * 0.3;
//            double userCurl = user.getCurl() * 0.3;
//            double userDistance = Math.sqrt(Math.pow(targetBench - userBench, 2) +
//                    Math.pow(targetSquat - userSquat, 2) +
//                    Math.pow(targetCurl - userCurl, 2));
//            distanceMap.put(userDistance, user);
//        }
//
//        // Get the top 10 matches
//        List<User> matches = new ArrayList<>();
//        int count = 0;
//        for (Map.Entry<Double, User> entry : distanceMap.entrySet()) {
//            if (count >= 3) {
//                break;
//            }
//            matches.add(entry.getValue());
//            count++;
//        }
//
//        return matches;
//    }






}
