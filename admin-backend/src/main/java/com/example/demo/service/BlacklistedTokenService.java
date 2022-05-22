package com.example.demo.service;

public interface BlacklistedTokenService {
    void blacklist(String token);

    boolean isAllowed(String token);
}
