package com.example.demo.service.impl;

import com.example.demo.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.ObjectDataCompiler;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    @Override
    public void instantiateTemplate(String templateName, DataProvider dataProvider) throws IOException {
        InputStream template = new FileInputStream("../rules-kjar/src/main/resources/templates/" + templateName + ".drt");
        DataProviderCompiler converter = new DataProviderCompiler();
        String compiledTemplateInstance = converter.compile(dataProvider, template);
        try (FileWriter writer = new FileWriter("../rules-kjar/src/main/resources/rules/" + templateName + ".drl")) {
            writer.write(compiledTemplateInstance);
        }
    }

    @Override
    public <T> void instantiateTemplate(String templateName, List<T> templateModels) throws IOException {
        InputStream template = new FileInputStream("../rules-kjar/src/main/resources/templates/" + templateName + ".drt");
        ObjectDataCompiler converter = new ObjectDataCompiler();
        String compiledTemplateInstance = converter.compile(templateModels, template);
        try (FileWriter writer = new FileWriter("../rules-kjar/src/main/resources/rules/" + templateName + ".drl")) {
            writer.write(compiledTemplateInstance);
        }
    }
}
