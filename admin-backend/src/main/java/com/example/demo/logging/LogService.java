package com.example.demo.logging;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;

    public void save(LogModel log) {
        logRepository.save(log);
    }

    public void info(String message) {
        saveLogType(message, LogType.INFO);
    }

    public void warning(String message) {
        saveLogType(message, LogType.WARNING);
    }

    public void error(String message) {
        saveLogType(message, LogType.ERROR);
    }

    private void saveLogType(String message, LogType type) {
        save(new LogModel(message, LocalDateTime.now(), type));
    }
}
