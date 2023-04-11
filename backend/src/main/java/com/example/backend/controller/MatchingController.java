
/**
 * This MatchingController class manages the http endpoints for the matching functionality of the application.
 */
package com.example.backend.controller;
import com.example.backend.MatchingStrategy;
import com.example.backend.MatchingType;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@CrossOrigin("http://localhost:3000")
public class MatchingController {

    private static MatchingController instance;
    private MatchingController (){}
    public MatchingController getInstance(){
        if(instance == null){
            instance = new MatchingController();
        }
        return instance;
    }
    @Autowired
    private Map<MatchingType, MatchingStrategy> matchingStrategies;

    @Autowired
    private UserRepository userRepository;

    /**
     * Returns the friends of a user.
     *
     * @param id the ID of the user.
     * @return the set of friends of the user.
     * @throws UserNotFoundException if the user with the given ID is not found.
     */
    @GetMapping("/user/{id}/friends")
    public Set<User> getUserFriends(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return user.getFriends();
    }

    /**
     * Adds a friend to the user's friends list.
     *
     * @param userId   the ID of the user.
     * @param friendId the ID of the friend to be added.
     * @return a response entity with a success message.
     * @throws UserNotFoundException if the user or friend with the given IDs are not found.
     */
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

    /**
     * Returns a list of users who match with the given user ID based on the specified matching type.
     *
     * @param id   the ID of the user.
     * @param type the matching type to be used.
     * @return a response entity with a list of matching users.
     */
    @GetMapping("/user/{id}/matches")
    public ResponseEntity<List<User>> getMatches(@PathVariable Long id,
                                                 @RequestParam(name = "type", defaultValue = "DEFAULT") MatchingType type) {
        List<User> matches = getMatchingStrategy(type).match(id);
        return ResponseEntity.ok(matches);
    }

    /**
     * Retrieves the matching strategy based on the specified matching type.
     *
     * @param type the matching type.
     * @return the matching strategy.
     */
    private MatchingStrategy getMatchingStrategy(MatchingType type) {
        return matchingStrategies.get(type);
    }
}
