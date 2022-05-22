package com.example.demo.service.impl;

import com.example.demo.model.BlacklistedToken;
import com.example.demo.repository.BlacklistedTokenRepository;
import com.example.demo.service.BlacklistedTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BlacklistedTokenServiceImpl implements BlacklistedTokenService {
    private final BlacklistedTokenRepository blacklistedTokenRepository;

    @Override
    @Transactional
    public void blacklist(String token) {
        var entry = blacklistedTokenRepository.findByToken(token);
        if (entry.isPresent()) {
            entry.get().setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        } else {
            blacklistedTokenRepository.save(new BlacklistedToken(token));
        }
    }

    @Override
    public boolean isAllowed(String token) {
        return blacklistedTokenRepository.findByToken(token).isEmpty();
    }
}
