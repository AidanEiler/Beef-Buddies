
//
package com.example.backend;
import java.util.List;

import com.example.backend.model.User;
import org.springframework.web.bind.annotation.PathVariable;

public interface MatchingStrategy {
    public List<User> match(@PathVariable Long id);
}



