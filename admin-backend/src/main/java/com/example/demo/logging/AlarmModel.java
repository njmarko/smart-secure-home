package com.example.demo.logging;

import com.example.demo.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter @Setter
public class AlarmModel {
    @Id
    private String id;
    private String message;
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime timestamp;
    
    public AlarmModel() {
    	this.timestamp = LocalDateTime.now();
    }
    
    public AlarmModel(String message) {
    	this();
    	this.message = message;
    }
}
