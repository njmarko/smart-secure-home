package com.example.demo.controller;

import com.example.demo.dto.SearchLogsRequest;
import com.example.demo.logging.AlarmModel;
import com.example.demo.logging.AlarmService;
import com.example.demo.logging.LogModel;
import com.example.demo.logging.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/logs")
@RequiredArgsConstructor
public class LogController {
    private final LogService logService;
    private final AlarmService alarmService;

    @PreAuthorize("hasAuthority('READ_LOGS')")
    @GetMapping
    public Page<LogModel> readLogs(@Valid SearchLogsRequest searchLogsRequest, Pageable pageable) {
        return logService.read(searchLogsRequest, pageable);
    }

    @PreAuthorize("hasAuthority('READ_ALARMS')")
    @GetMapping("/alarms")
    public Page<AlarmModel> readAlarms(Pageable pageable) {
        return alarmService.read(pageable);
    }

    @PreAuthorize("hasAuthority('ACKNOWLEDGE_ALARMS')")
    @DeleteMapping("/alarms/{id}")
    public void acknowledgeAlarm(@PathVariable String id) {
        alarmService.acknowledge(id);
    }
}
