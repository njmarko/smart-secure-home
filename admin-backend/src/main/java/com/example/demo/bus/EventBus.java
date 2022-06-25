package com.example.demo.bus;


import com.example.demo.events.AlarmOccurred;
import com.example.demo.events.BaseAlarm;
import com.example.demo.logging.AlarmModel;
import com.example.demo.logging.AlarmService;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventBus {
	private final SimpMessagingTemplate simpMessagingTemplate;
	private final AlarmService alarmService;

	public void onAlarm(AlarmOccurred alarmOccured) {
		this.simpMessagingTemplate.convertAndSend("/live-alarms", alarmOccured);
	}
	
	public void onAlarm(BaseAlarm alarm) {
		AlarmOccurred alarmOccurred = alarm.alarm();
		AlarmModel alarmModel = new AlarmModel(alarmOccurred.getMessage());
		this.onAlarm(alarmOccurred);
		this.alarmService.save(alarmModel);
	}
	
}
