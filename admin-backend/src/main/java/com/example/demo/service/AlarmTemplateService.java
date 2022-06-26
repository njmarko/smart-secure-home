package com.example.demo.service;

import com.example.demo.model.AlarmTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AlarmTemplateService {

    AlarmTemplate createAlarmRule(AlarmTemplate alarmTemplate);

    void deleteAlarmRule(String id);

    Page<AlarmTemplate> read(Pageable pageable);
}
