package com.example.demo.logging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LogModel {
    private String content;
    private LocalDateTime timestamp;
    private LogType logType;
}
