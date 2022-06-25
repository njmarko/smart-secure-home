package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmOccured {
	private String message;
	private LocalDateTime timestamp;
	
	public AlarmOccured(String message) {
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}
}
