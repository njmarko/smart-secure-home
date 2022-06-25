package com.example.demo.logging;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmService {
	private final AlarmRepository alarmRepository;
	
	public AlarmModel save(AlarmModel alarm) {
		return alarmRepository.save(alarm);
	}
}
