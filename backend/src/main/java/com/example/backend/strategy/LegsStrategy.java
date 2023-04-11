
//src/main/java/com/example/backend/strategy/LegsStrategy.java
/**
 * Matching strategy that matches users based on their leg exercises (squats).
 * Assigns weights to each value and calculates distance between users based on those weights.
 */
package com.example.backend.strategy;
import java.util.List;
import java.util.TreeMap;

import com.example.backend.MatchingStrategy;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class LegsStrategy implements MatchingStrategy{

    @Autowired
    UserRepository userRepository;

    /**
     * Matches a user with other users based on their leg exercises (curls).
     *
     * @param id the ID of the user to match with others
     * @return a list of matched users, sorted by distance (closest to farthest)
     * @throws UserNotFoundException if the user with the given ID is not found in the database
     */
    @Override
    public List<User> match(Long id) {
        User targetUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        double targetBench = targetUser.getBench() * 0.1; // Assign weights to each value
        double targetSquat = targetUser.getSquat() * 0.8;
        double targetCurl = targetUser.getCurl() * 0.1;

        List<User> allUsers = (List<User>) userRepository.findAll();
        allUsers.remove(targetUser); // Remove the target user from the list

        // Calculate distance for each user and add to a TreeMap
        TreeMap<Double, User> distanceMap = new TreeMap<>();
        for (User user : allUsers) {
            double userBench = user.getBench() * 0.1;
            double userSquat = user.getSquat() * 0.8;
            double userCurl = user.getCurl() * 0.1;
            double userDistance = Math.sqrt(Math.pow(targetBench - userBench, 2) +
                    Math.pow(targetSquat - userSquat, 2) +
                    Math.pow(targetCurl - userCurl, 2));
            distanceMap.put(userDistance, user);
        }

        // Get the top 10 matches
        List<User> matches = new ArrayList<>();
        int count = 0;
        for (Map.Entry<Double, User> entry : distanceMap.entrySet()) {
            if (count >= 10) {
                break;
            }
            double distance = entry.getKey();
            User user = entry.getValue();
            matches.add(user);
            user.setDistance(distance); // Set the distance on the user object
            count++;
        }

        return matches;
    }


}
