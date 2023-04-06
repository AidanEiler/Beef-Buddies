package com.example.demo;

import java.util.List;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.Map;

public class LegsStrategy implements MatchingStrategy{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> match(Long id) {
		User targetUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        double targetBench = targetUser.getBench() * 0.1; // Assign weights to each value
        double targetSquat = targetUser.getSquat() * 0.6;
        double targetCurl = targetUser.getCurl() * 0.1;

        List<User> allUsers = (List<User>) userRepository.findAll();
        allUsers.remove(targetUser); // Remove the target user from the list

        // Calculate distance for each user and add to a TreeMap
        TreeMap<Double, User> distanceMap = new TreeMap<>();
        for (User user : allUsers) {
            double userBench = user.getBench() * 0.4;
            double userSquat = user.getSquat() * 0.3;
            double userCurl = user.getCurl() * 0.3;
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
            matches.add(entry.getValue());
            count++;
        }

        return matches;
	}

}
