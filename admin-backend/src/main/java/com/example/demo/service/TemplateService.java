package com.example.demo.service;

import org.drools.template.DataProvider;

import java.io.IOException;
import java.util.List;

public interface TemplateService {
    void instantiateTemplate(String templateName, DataProvider dataProvider) throws IOException;
    <T> void instantiateTemplate(String templateName, List<T> templateModels) throws IOException;
}
