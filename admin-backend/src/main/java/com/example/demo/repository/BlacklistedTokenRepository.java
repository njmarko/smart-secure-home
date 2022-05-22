package com.example.demo.repository;

import com.example.demo.model.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Integer> {
    Optional<BlacklistedToken> findByToken(String token);
}
