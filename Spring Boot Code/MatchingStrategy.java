package com.example.demo;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

public interface MatchingStrategy {
	public List<User> match(@PathVariable Long id);
}
