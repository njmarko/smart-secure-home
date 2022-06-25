package com.example.demo.events;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmOccurred {
	private String message;
	private LocalDateTime timestamp;
	
	public AlarmOccurred(String message) {
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}
}
