package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndIsActiveTrue(String username);

    Optional<User> findByIdAndIsActiveTrue(Integer id);

    @Query("select u from User u join fetch u.roles where u.isActive = true")
    List<User> read();
}

