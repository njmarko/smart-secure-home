package com.example.demo.service;

import com.example.demo.model.DeviceAlarmTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeviceAlarmTemplateService {

    DeviceAlarmTemplate createAlarmRule(DeviceAlarmTemplate alarmTemplate);

    void deleteAlarmRule(String id);

    Page<DeviceAlarmTemplate> read(Pageable pageable);

    void deleteAll();
}
