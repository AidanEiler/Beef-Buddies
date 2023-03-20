package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * Implements the UserRepository methods
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsByEmailOrUsername(String email, String username) {
        return userRepository.existsByEmailOrUsername(email, username);
    }

    public List<User> findAllByOrderByUsernameAsc() {
        return userRepository.findAllByOrderByUsernameAsc();
    }

    public User findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public User findById(Long id) {
        return userRepository.findById(id);
    }
}
