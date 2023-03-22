package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	private AuthenticationManager auth;
	
	/*
	 * Uses the matcher to run the matching algorithm on the two users
	 * @param user1 User that is logged in at the time
	 * @param user2 User that is grabbed from the database to be compared to user1
	 * @return match Determines whether or not a match has been found
	 */
	@RequestMapping("matchmake")
	public boolean Matchmake(User user1, User user2) {
		boolean match = false;
		//processing is a placeholder here
		//need to iterate through the database as long as there are users to sift through
		while(!match) {
			match = matcher.compareStats(user1, user2);
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
	@RequestMapping("matchmaking")
	public ModelAndView matchmakingUI(@RequestParam("userId1") long id1, @RequestParam("userId2") long id2)
	{
		ModelAndView mv=new ModelAndView();
		mv.addObject("userId1",id1);
		mv.addObject("userId2", id2);
		mv.setViewName("matchmaking");
		return mv;
	}
}
