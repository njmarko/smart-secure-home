package com.example.demo.repository;

import com.example.demo.model.CSRVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CSRVerificationTokenRepository extends JpaRepository<CSRVerificationToken, Integer> {

    Optional<CSRVerificationToken> findByToken(String token);
}
