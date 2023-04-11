//src/main/java/com/example/backend/repository/UserRepository.java
package com.example.backend.repository;

import com.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Retrieves a User entity from the database based on the provided username.
     *
     * @param username the username to search for
     * @return the User entity with the matching username
     */
    User findByUsername(String username);

    /**
     * Retrieves basic information about all User entities in the database, excluding the provided User ID and a list of friend IDs.
     *
     * @param excludeUserId the ID of the user to exclude from the query results
     * @param friendIds     a List of IDs of friends to exclude from the query results
     * @return a List of Maps containing basic information about each User entity, including ID, username, first name, and last name
     */
    @Query(value = "SELECT new map(u.id as id, u.username as username, u.first_name as firstName, u.last_name as lastName) " +
            "FROM User u " +
            "WHERE u.id != :excludeUserId " +
            "AND u.id NOT IN :friendIds")
    List<Map<String, Object>> findAllBasicInfoExcept(Long excludeUserId, List<Long> friendIds);

    /**
     * Deletes all rows from the user_friends table where the user ID matches the provided ID.
     *
     * @param userId the ID of the user whose friends should be deleted
     */
    @Query(value = "DELETE FROM user_friends WHERE user_id = :userId", nativeQuery = true)
    void deleteFriendsByUserId(Long userId);

}
