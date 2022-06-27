package com.example.demo.service.impl;

import com.example.demo.service.DeviceTemplateService;
import lombok.RequiredArgsConstructor;
import org.drools.template.ObjectDataCompiler;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceTemplateServiceImpl implements DeviceTemplateService {

    @Override
    public <T> void instantiateTemplate(String templateName, List<T> templateModels) throws IOException {
        InputStream template = new FileInputStream("../device-rules-kjar/src/main/resources/templates/" + templateName + ".drt");
        ObjectDataCompiler converter = new ObjectDataCompiler();
        String compiledTemplateInstance = converter.compile(templateModels, template);
        try (FileWriter writer = new FileWriter("../device-rules-kjar/src/main/resources/rules/" + templateName + ".drl")) {
            writer.write(compiledTemplateInstance);
        }
    }
}
