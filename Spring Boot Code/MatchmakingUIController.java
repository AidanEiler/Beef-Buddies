package com.example.demo;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/*
 * Handles requests for the matchmake page as well as requests for the
 * matchmaking algorithm to be run
 */
@Controller
public class MatchmakingUIController
{
	
	@Autowired
	private Matcher matcher;
	
	
	@Autowired
	private UserRepository userRepo;
	
	List<User> matches = new ArrayList<>();
	
	/*
	 * Uses the matcher to run the matching algorithm on the two users
	 * @param user1 User that is logged in at the time
	 * @param user2 User that is grabbed from the database to be compared to user1
	 * @return match Determines whether or not a match has been found
	 */
	@RequestMapping("matchmake")
	public boolean Matchmake(@RequestBody User user1, @RequestBody User user2) {
		boolean match = false;
		int delta = 10;
		while(!match) {
			match = matcher.compareStats(user1, user2, delta);
			if(!match) {
				user2 = userRepo.findById((long)user2.getId()+1);//Need to add exception handling for when the user does not exist
			}
		}
		if(match) {
			userRepo.save(user2);
			matches.add(user2);
		}
		return match;
	}
	
	/*
	 * Handles requests for the matchmaking page and gets the ID's of the users
	 * to be compared and displayed on the page
	 * @param id1 ID of the logged in user that queries the database for a user
	 * @param id2 ID of the user pulled from the database that queries the database for that user
	 * @return ModelAndView object that contains the user info and the matchmaking
	 * page to be displayed
	 */
	@GetMapping("/matches")
    public List<User> getMatches() {
        return matches;
    }
}

