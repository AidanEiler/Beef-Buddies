
//
package com.example.backend;
import java.util.List;

import com.example.backend.model.User;
import org.springframework.web.bind.annotation.PathVariable;

public interface MatchingStrategy {
    /**
     * Matches users based on their fitness preferences.
     *
     * @param id the ID of the user for whom to find matches
     * @return a List of User entities that match the preferences of the user with the provided ID
     */
    public List<User> match(@PathVariable Long id);
}



