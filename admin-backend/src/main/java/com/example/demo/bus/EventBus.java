package com.example.demo.bus;


import com.example.demo.events.AlarmOccurred;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventBus {
	private final SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	public EventBus(SimpMessagingTemplate simpMessagingTemplate) {
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	public void onAlarm(AlarmOccurred alarmOccured) {
		this.simpMessagingTemplate.convertAndSend("/live-alarms", alarmOccured);
	}
	
}
