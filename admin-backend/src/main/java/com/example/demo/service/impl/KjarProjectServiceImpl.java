package com.example.demo.service.impl;

import com.example.demo.service.KjarProjectService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KjarProjectServiceImpl implements KjarProjectService {
    @Override
    public void compileKjarProject() throws IOException, InterruptedException {
        String workingDir = System.getProperty("user.dir");
        String command = workingDir + "\\compileKjar.bat";
        Runtime.getRuntime().exec(command).waitFor();
    }
}
