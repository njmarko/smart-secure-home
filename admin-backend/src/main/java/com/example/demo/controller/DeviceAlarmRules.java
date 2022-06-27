package com.example.demo.controller;

import com.example.demo.dto.CreateAlarmRuleRequest;
import com.example.demo.model.DeviceAlarmTemplate;
import com.example.demo.service.DeviceAlarmTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/device-alarm-rules")
@RequiredArgsConstructor
public class DeviceAlarmRules {

    private final DeviceAlarmTemplateService deviceAlarmTemplateService;

    @PreAuthorize("hasAuthority('MANAGE_DEVICE_ALARM_RULES')")
    @GetMapping
    public Page<DeviceAlarmTemplate> read(Pageable pageable) {
        return deviceAlarmTemplateService.read(pageable);
    }

    @PreAuthorize("hasAuthority('MANAGE_DEVICE_ALARM_RULES')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceAlarmTemplate create(@Valid @RequestBody CreateAlarmRuleRequest request) {
        return deviceAlarmTemplateService.createAlarmRule(new DeviceAlarmTemplate(
                request.getName(), request.getWhen(), request.getThen(), request.getMessage()
        ));
    }

    @PreAuthorize("hasAuthority('MANAGE_DEVICE_ALARM_RULES')")
    @DeleteMapping("/{id}")
    public void deactivateRule(@PathVariable String id) {
        deviceAlarmTemplateService.deleteAlarmRule(id);
    }
}
