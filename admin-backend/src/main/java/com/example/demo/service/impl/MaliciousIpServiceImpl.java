package com.example.demo.service.impl;

import com.example.demo.model.MaliciousIpTemplate;
import com.example.demo.service.KjarProjectService;
import com.example.demo.service.MaliciousIpService;
import com.example.demo.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaliciousIpServiceImpl implements MaliciousIpService {
    private final TemplateService templateService;
    private final KjarProjectService kjarProjectService;

    @Override
    public void loadAndInsertMaliciousIps() {
        List<MaliciousIpTemplate> maliciousIpTemplates = new ArrayList<>();
        try (BufferedReader br  = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/static/maliciousIps.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                maliciousIpTemplates.add(new MaliciousIpTemplate(line.trim()));
            }

            templateService.instantiateTemplate("malicious-ip", maliciousIpTemplates);
            kjarProjectService.compileKjarProject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
