package com.example.demo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

/*
 * Repository that stores the user info
 */
public interface UserRepository extends CrudRepository<User, Integer> {
	/*
	 * Finds user by email
	 * @param email User's email
	 * @return User attached to email
	 */
	User findByEmail(String email);
	/*
	 * Finds user by username
	 * @param username User's username
	 * @return User attached to username
	 */
    User findByUsername(String username);
    /*
     * Checks if a user with the username or email already exists
     * @param email Inputed email
     * @param username Inputed username
     * @return False if the user does not exist and true if it does
     */
    boolean existsByEmailOrUsername(String email, String username);
    /*
     * Lists all users in ascending order
     * @return A list of all users
     */
    List<User> findAllByOrderByUsernameAsc();
    /*
     * Finds a user using an email and password
     * @param email Inputed email
     * @param Inputed password
     * @return User associated with the email and password
     */
    User findByEmailAndPassword(String email, String password); 
    /*
     * Finds a user using a user id
     * @param id Inputed id
     * @return User associated with that id
     */
    User findById(Long id);
}
