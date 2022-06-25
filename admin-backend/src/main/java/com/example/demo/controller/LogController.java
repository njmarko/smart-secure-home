package com.example.demo.controller;

import java.util.List;

import com.example.demo.logging.AlarmModel;
import com.example.demo.logging.AlarmService;
import com.example.demo.logging.LogModel;
import com.example.demo.logging.LogService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/logs")
@RequiredArgsConstructor
public class LogController {
	private final LogService logService;
	private final AlarmService alarmService;
	
	@PreAuthorize("hasAuthority('READ_LOGS')")
	@GetMapping
	public Page<LogModel> readLogs(Pageable pageable) {
		return logService.read(pageable);
	}
	
	@PreAuthorize("hasAuthority('READ_ALARMS')")
	@GetMapping("/alarms")
	public Page<AlarmModel> readAlarms(Pageable pageable) {
		return alarmService.read(pageable);
	}
}