
// com/example/backend/controller/UserController.java
/**

 *The UserController class handles HTTP requests related to the User model and interacts with the UserRepository.
 */
package com.example.backend.controller;
import com.example.backend.MatchingStrategy;
import com.example.backend.MatchingType;
import com.example.backend.model.User;
import com.example.backend.repository.MessageRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.backend.exception.UserNotFoundException;
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
    private Map<MatchingType, MatchingStrategy> matchingStrategies;

    /**
     * Creates a new user and saves it to the UserRepository.
     * If the user's bench value is null, sets it to 0.
     * @param newUser a User object representing the new user to be created
     * @return a User object representing the newly created user
     */
    @PostMapping("/user")
    User newUser(@RequestBody User newUser) {
        if (newUser.getBench() == null) {
            newUser.setBench(0L);
        }
        return userRepository.save(newUser);
    }

    /**
     * Gets a list of all the users in the UserRepository.
     * @return a List of User objects representing all the users in the UserRepository
     */
    @GetMapping("/users")
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Gets a user with a given ID from the UserRepository.
     * @param id a Long representing the ID of the user to be retrieved
     * @return a User object representing the user with the given ID
     * @throws UserNotFoundException if the user with the given ID does not exist in the UserRepository
     */
    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Gets a user with a given username from the UserRepository.
     * @param username a String representing the username of the user to be retrieved
     * @return a User object representing the user with the given username
     */
    @GetMapping("/user")
    User getUserByUsername(@RequestParam String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Updates a user with a given ID in the UserRepository.
     * @param newUser a User object representing the updated user
     * @param id a Long representing the ID of the user to be updated
     * @return a User object representing the updated user
     * @throws UserNotFoundException if the user with the given ID does not exist in the UserRepository
     */
    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
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

                    return userRepository.save(user);

                }).orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Retrieves basic information of all users excluding the given user and their friends
     * @param excludeUserId the ID of the user to exclude from the result
     * @return a list of maps containing basic information of each user
     */
    @GetMapping("/users/basic")
    public List<Map<String, Object>> getAllUsersBasicInfo(@RequestParam("exclude") Long excludeUserId) {
        User excludeUser = userRepository.findById(excludeUserId)
                .orElseThrow(() -> new UserNotFoundException(excludeUserId));

        List<Long> friendIds = excludeUser.getFriends().stream().map(User::getId).collect(Collectors.toList());
        friendIds.add(excludeUserId); // Add the exclude user ID to the list

        return userRepository.findAllBasicInfoExcept(excludeUserId, friendIds);
    }


//
//    @GetMapping("/user/{id}/friends")
//    public Set<User> getUserFriends(@PathVariable Long id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new UserNotFoundException(id));
//        return user.getFriends();
//    }
//
//
//
//    @PostMapping("/user/{userId}/addFriend/{friendId}")
//    public ResponseEntity<?> addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException(userId));
//        User friend = userRepository.findById(friendId)
//                .orElseThrow(() -> new UserNotFoundException(friendId));
//
//        user.addFriend(friend);
//
//        userRepository.save(user);
//
//        return ResponseEntity.ok().body("Friend added successfully");
//    }
//
//    private MatchingStrategy getMatchingStrategy(MatchingType type) {
//        return matchingStrategies.get(type);
//    }
//
//    @GetMapping("/user/{id}/matches")
//    public ResponseEntity<List<User>> getMatches(@PathVariable Long id,
//                                                 @RequestParam(name = "type", defaultValue = "DEFAULT") MatchingType type) {
//        List<User> matches = getMatchingStrategy(type).match(id);
//        return ResponseEntity.ok(matches);
//    }




//
//    @GetMapping("/user/{id}/matches")
//    public ResponseEntity<List<User>> getMatches(@PathVariable Long id) {
//        List<User> matches = defaultStrategy.match(id);
//        return ResponseEntity.ok(matches);
//    }
//    @GetMapping("/user/{id}/armsMatches")
//    public ResponseEntity<List<User>>  getArmsMatches(@PathVariable Long id){
//        List<User> matches = armsStrategy.match(id);
//        return ResponseEntity.ok(matches);
//    }
//
//    @GetMapping("/user/{id}/legsMatches")
//    public ResponseEntity<List<User>>  getLegMatches(@PathVariable Long id){
//        List<User> matches = legsStrategy.match(id);
//        return ResponseEntity.ok(matches);
//    }


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
