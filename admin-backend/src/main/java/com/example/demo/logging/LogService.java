package com.example.demo.logging;

import lombok.RequiredArgsConstructor;

import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import com.example.demo.bus.EventBus;
import com.example.demo.events.AlarmOccurred;
import com.example.demo.events.BaseEvent;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;
    private final KieSession kieSession;
    private final EventBus eventBus;
    
    public void save(BaseEvent event) {
    	this.save(event.log());
    	kieSession.insert(event);
    	kieSession.fireAllRules();
    }

    public void save(LogModel log) {
        logRepository.save(log);
        if (log.getLogType() == LogType.ERROR) {
        	eventBus.onAlarm(new AlarmOccurred("ERROR log occured."));
        }
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
