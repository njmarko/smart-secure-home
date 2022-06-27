package com.example.demo.service;

import java.io.IOException;

public interface KjarProjectService {
    void compileKjarProject() throws IOException, InterruptedException;

    void compileDeviceKjarProject() throws IOException, InterruptedException;
}
