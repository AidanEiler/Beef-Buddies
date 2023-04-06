package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/*
 * Object that contains and executes the matching algorithm
 */
@Service
public class Matcher {
	private MatchingStrategy strat;
	
	public Matcher(MatchingStrategy matchingStrat) {
		strat = matchingStrat;
	}
	
	public List<User> match(@PathVariable Long id) {
		return strat.match(id);
	}
}
