package com.example.marketplace3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.marketplace3.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}