package com.example.demo.service.impl;

import com.example.demo.model.AlarmTemplate;
import com.example.demo.repository.AlarmTemplateRepository;
import com.example.demo.service.AlarmTemplateService;
import com.example.demo.service.KjarProjectService;
import com.example.demo.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmTemplateServiceImpl implements AlarmTemplateService {
    private final AlarmTemplateRepository alarmTemplateRepository;
    private final KjarProjectService kjarProjectService;
    private final TemplateService templateService;

    @Override
    public AlarmTemplate createAlarmRule(AlarmTemplate alarmTemplate) {
        AlarmTemplate created = alarmTemplateRepository.save(alarmTemplate);

        instantiateTemplates();
        return created;
    }

    @Override
    public void deleteAlarmRule(String id) {
        AlarmTemplate alarmTemplate = this.alarmTemplateRepository.findById(id).orElseThrow();
        alarmTemplate.setActive(false);
        alarmTemplateRepository.save(alarmTemplate);

        instantiateTemplates();
    }

    @Override
    public Page<AlarmTemplate> read(Pageable pageable) {
        return alarmTemplateRepository.findActive(pageable);
    }

    private void instantiateTemplates() {
        List<AlarmTemplate> alarmTemplates = alarmTemplateRepository.findActive();
        try {
            templateService.instantiateTemplate("alarms", alarmTemplates);
            kjarProjectService.compileKjarProject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
