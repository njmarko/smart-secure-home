package com.example.demo.service;

import org.springframework.scheduling.annotation.Async;

public interface EmailService {

    @Async
    void sendMessage(String to, String subject, String text);
}
