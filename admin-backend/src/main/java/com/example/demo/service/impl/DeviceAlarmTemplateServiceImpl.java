package com.example.demo.service.impl;

import com.example.demo.model.DeviceAlarmTemplate;
import com.example.demo.repository.DeviceAlarmTemplateRepository;
import com.example.demo.service.DeviceAlarmTemplateService;
import com.example.demo.service.DeviceTemplateService;
import com.example.demo.service.KjarProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceAlarmTemplateServiceImpl implements DeviceAlarmTemplateService {

    private final DeviceAlarmTemplateRepository alarmTemplateRepository;
    private final KjarProjectService kjarProjectService;
    private final DeviceTemplateService templateService;

    @Override
    public DeviceAlarmTemplate createAlarmRule(DeviceAlarmTemplate alarmTemplate) {
        DeviceAlarmTemplate created = alarmTemplateRepository.save(alarmTemplate);

        instantiateTemplates();
        return created;
    }

    @Override
    public void deleteAlarmRule(String id) {
        DeviceAlarmTemplate alarmTemplate = this.alarmTemplateRepository.findById(id).orElseThrow();
        alarmTemplate.setActive(false);
        alarmTemplateRepository.save(alarmTemplate);

        instantiateTemplates();
    }

    @Override
    public Page<DeviceAlarmTemplate> read(Pageable pageable) {
        return alarmTemplateRepository.findActive(pageable);
    }

    private void instantiateTemplates() {
        List<DeviceAlarmTemplate> alarmTemplates = alarmTemplateRepository.findActive();
        try {
            templateService.instantiateTemplate("alarms", alarmTemplates);
            kjarProjectService.compileKjarProject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
