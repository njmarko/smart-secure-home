package com.example.demo.logging;

import java.time.LocalDateTime;

import com.example.demo.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LogModel {
    private String content;
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime timestamp;
    private LogType logType;
    
    public static LogModel info(String content) {
    	return new LogModel(content, LocalDateTime.now(), LogType.INFO);
    }
    
    public static LogModel warning(String content) {
    	return new LogModel(content, LocalDateTime.now(), LogType.WARNING);
    }
    
    public static LogModel error(String content) {
    	return new LogModel(content, LocalDateTime.now(), LogType.ERROR);
    }
}
