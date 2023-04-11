// src/main/java/com/example/backend/BackendApplication.java
package com.example.backend;

import com.example.backend.strategy.ArmsStrategy;
import com.example.backend.strategy.DefaultStrategy;
import com.example.backend.strategy.LegsStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class BackendApplication {
	/**
	 * The main method starts the Spring Boot application.
	 *
	 * @param args command line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	/**
	 * Provides a bean for a map of matching strategies.
	 *
	 * The map contains the available matching strategies, keyed by their matching type (DEFAULT, ARMS, or LEGS).
	 *
	 * @param defaultStrategy the DefaultStrategy bean
	 * @param armsStrategy    the ArmsStrategy bean
	 * @param legsStrategy    the LegsStrategy bean
	 * @return a map of matching strategies
	 */
	@Bean
	public Map<MatchingType, MatchingStrategy> matchingStrategies(DefaultStrategy defaultStrategy,
																  ArmsStrategy armsStrategy,
																  LegsStrategy legsStrategy) {
		Map<MatchingType, MatchingStrategy> strategies = new HashMap<>();
		strategies.put(MatchingType.DEFAULT, defaultStrategy);
		strategies.put(MatchingType.ARMS, armsStrategy);
		strategies.put(MatchingType.LEGS, legsStrategy);
		return strategies;
	}

}
