package com.example.demo.logging;

import java.time.LocalDateTime;

import com.example.demo.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document
@Getter @Setter
public class AlarmModel {
    private String content;
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime timestamp;
    
    public AlarmModel() {
    	this.timestamp = LocalDateTime.now();
    }
    
    public AlarmModel(String content) {
    	this();
    	this.content = content;
    }
}
