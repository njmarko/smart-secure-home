package com.example.demo.controller;

import com.example.demo.dto.CreateAlarmRuleRequest;
import com.example.demo.model.AlarmTemplate;
import com.example.demo.service.AlarmTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/alarm-rules")
@RequiredArgsConstructor
public class AlarmRulesController {
    private final AlarmTemplateService alarmTemplateService;

    @PreAuthorize("hasAuthority('MANAGE_ALARM_RULES')")
    @GetMapping
    public Page<AlarmTemplate> read(Pageable pageable) {
        return alarmTemplateService.read(pageable);
    }

    @PreAuthorize("hasAuthority('MANAGE_ALARM_RULES')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlarmTemplate create(@Valid @RequestBody CreateAlarmRuleRequest request) {
        return alarmTemplateService.createAlarmRule(new AlarmTemplate(
                request.getName(), request.getWhen(), request.getThen(), request.getMessage()
        ));
    }

    @PreAuthorize("hasAuthority('MANAGE_ALARM_RULES')")
    @DeleteMapping("/{id}")
    public void deactivateRule(@PathVariable String id) {
        alarmTemplateService.deleteAlarmRule(id);
    }
}
