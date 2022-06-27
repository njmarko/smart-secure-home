package com.example.demo.service;

import java.io.IOException;
import java.util.List;

public interface DeviceTemplateService {
    <T> void instantiateTemplate(String templateName, List<T> templateModels) throws IOException;
}
