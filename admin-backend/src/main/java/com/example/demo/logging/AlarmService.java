package com.example.demo.logging;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmService {
	private final AlarmRepository alarmRepository;
	
	public AlarmModel save(AlarmModel alarm) {
		return alarmRepository.save(alarm);
	}

	public Page<AlarmModel> read(Pageable pageable) {
		return alarmRepository.findAll(pageable);
	}
}
