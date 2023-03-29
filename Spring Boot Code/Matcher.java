package com.example.demo;

import org.springframework.stereotype.Service;

/*
 * Object that contains and executes the matching algorithm
 */
@Service
public class Matcher {
	
	/*
	 * Matching algorithm that determines the compatibility between two users
	 * by checking if their weight values are within the range of 10 pounds
	 * @param user1 The user who is logged in at the time
	 * @param user2 The user who is pulled from the database to be compared to user1
	 * @return A boolean value that is false when a match is not found and there are
	 * no more users to compare and is true when a match is found
	 */
	public boolean compareStats(User user1, User user2, int delta) {
		  boolean benchMatch = false;
		  boolean curlMatch = false;
		  boolean squatMatch = false;
		  int rangeCheck = user1.getBench() - user2.getBench();
		  if(rangeCheck < 0){
			  rangeCheck *= -1;
		  }
		  if(rangeCheck <= delta) {
			  benchMatch = true;
			  rangeCheck = user1.getCurl() - user2.getCurl();
			  if(rangeCheck < 0) {
				  rangeCheck *= -1;
			  }
		  }
		  if(rangeCheck <= delta) {
			  curlMatch = true;
			  rangeCheck = user1.getSquat() - user2.getSquat();
			  if(rangeCheck < 0) {
				  rangeCheck *= -1;
			  }
		  }
		  if(rangeCheck <= delta) {
			  squatMatch = true;
		  }
		  if(benchMatch && curlMatch && squatMatch) {
			  return true;
		  }
		  return false;
	}
}
