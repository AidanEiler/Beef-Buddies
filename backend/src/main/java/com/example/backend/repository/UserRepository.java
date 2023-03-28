package com.example.backend.repository;

import com.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);

    @Query("SELECT new map(u.id as id, u.username as username, u.first_name as firstName, u.last_name as lastName) FROM User u WHERE u.id <> :excludeUserId")
    List<Map<String, Object>> findAllBasicInfoExcept(Long excludeUserId);}
