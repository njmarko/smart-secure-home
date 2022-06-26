package com.example.demo.logging;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlarmService {
	private final AlarmRepository alarmRepository;
	
	public AlarmModel save(AlarmModel alarm) {
		return alarmRepository.save(alarm);
	}

	public Page<AlarmModel> read(Pageable pageable) {
		return alarmRepository.readNonAcknowledged(pageable);
	}

	public void acknowledge(String id) {
		AlarmModel alarm = alarmRepository.findById(id).orElse(null);
		if (alarm == null) {
			return;
		}
		alarm.setAcknowledged(true);
		alarmRepository.save(alarm);
	}
}
