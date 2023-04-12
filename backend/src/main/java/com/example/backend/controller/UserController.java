
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

    // Singleton instance variable
    private static UserController instance;

    // Private constructor to prevent instantiation of UserController
    private UserController() {}

    /**
     * Returns the Singleton instance of the UserController class.
     * @return the Singleton instance of the UserController class
     */
    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

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


}
